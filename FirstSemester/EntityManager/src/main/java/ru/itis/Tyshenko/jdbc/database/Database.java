package ru.itis.Tyshenko.jdbc.database;

import lombok.SneakyThrows;
import ru.itis.Tyshenko.converter.FieldToStringConverter;
import ru.itis.Tyshenko.converter.DatabaseType;
import ru.itis.Tyshenko.converter.UnknownFieldTypeException;
import ru.itis.Tyshenko.jdbc.criteria.Query;
import ru.itis.Tyshenko.jdbc.template.CustomRowMapper;
import ru.itis.Tyshenko.jdbc.template.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public abstract class Database implements Manager {

    protected final JdbcTemplate template;
    protected final String TABLE_NAME_PATTERN = ":table";
    protected final String FIELD_NAME_PATTERN = ":fieldsName";
    protected final String FIELD_NAME_WITH_TYPE_PATTERN = ":fields";
    protected final String FIELD_VALUES = ":fieldValues";
    protected final DatabaseType databaseType;
    private final FieldToStringConverter converterForDB;
    private SqlReplacer sqlReplacer;

    public Database(DataSource dataSource, DatabaseType DatabaseType) {
        this.databaseType = DatabaseType;
        converterForDB = new FieldToStringConverter(DatabaseType);
        template = new JdbcTemplate(dataSource);
        sqlReplacer = new SqlReplacer(TABLE_NAME_PATTERN, FIELD_NAME_PATTERN, FIELD_NAME_WITH_TYPE_PATTERN, FIELD_VALUES, converterForDB);
    }

    public DatabaseType getType() {
        return databaseType;
    }

    public abstract <T> void createTable(String table, Class<T> entityClass);

    public abstract <T> List<T> findAll(String table, Class<T> entityClass);

    protected <T> List<T> findAll(String table, Class<T> entityClass, String sql) {
        try {
            return template.query(sql, getRowMapper(entityClass));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SneakyThrows
    public  <T> List<T> find(String tableName, Class<T> tClass, String sql) {
        return template.query(sql, getRowMapper(tClass));
    }

    protected <T> void createTable(String tableName, Class<T> entityClass, String SQL_CREATE_TABLE) {
        try {
            template.execute(sqlReplacer.insertValues(SQL_CREATE_TABLE, entityClass, null, tableName));
        } catch (SQLException | UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract void save(String table, Object object);

    protected void save(String tableName, Object entity, String SQL_INSERT) {
        try {
            template.execute(sqlReplacer.insertValues(SQL_INSERT, entity.getClass(), entity, tableName));
        } catch (SQLException | IllegalAccessException | UnknownFieldTypeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract  <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue);

    protected  <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue, String SQL_SELECT_BY_ID) {
        try {
            return template.queryForObject(sqlReplacer.insertValues(SQL_SELECT_BY_ID, resultType, null, tableName), getRowMapper(resultType), idValue);
        } catch (SQLException | UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> String createSqlStringFromCriteria(Query query, String tableName) {
        try {
            String SQL_SELECT_BY_ID = "select " + FIELD_NAME_PATTERN + " from " + TABLE_NAME_PATTERN + " where ";
            StringBuilder sql = new StringBuilder(sqlReplacer.insertValues(SQL_SELECT_BY_ID, query.getRoot().getTClass(), null, tableName));
            query.getExpressions().forEach(expression -> sql.append(" ").append(expression.getJoinType().toString())
            .append(" ").append(expression.getKey()).append(expression.getOperator().toString())
                    .append("'").append(expression.getValue()).append("'"));
            return sql.toString();
        } catch (UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private <T> CustomRowMapper<T> getRowMapper(Class<T> resultType) {
        return result -> {
            T object;
            try {
                object = resultType.getConstructor().newInstance();
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field: fields) {
                    resultType.getMethod("set" +
                            String.valueOf(field.getName().charAt(0)).toUpperCase(Locale.ROOT) +
                            field.getName().substring(1), field.getType())
                            .invoke(object, result.getObject(field.getName()));
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
            return object;
        };
    }
}
