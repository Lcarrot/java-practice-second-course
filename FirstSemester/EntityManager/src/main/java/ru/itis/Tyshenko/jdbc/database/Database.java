package ru.itis.Tyshenko.jdbc.database;

import ru.itis.Tyshenko.converter.FieldToStringConverter;
import ru.itis.Tyshenko.converter.IDatabase;
import ru.itis.Tyshenko.converter.UnknownFieldTypeException;
import ru.itis.Tyshenko.jdbc.template.CustomRowMapper;
import ru.itis.Tyshenko.jdbc.template.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public abstract class Database {

    protected final JdbcTemplate template;
    protected final String TABLE_NAME_PATTERN = ":table";
    protected final String FIELD_NAME_PATTERN = ":fieldsName";
    protected final String FIELD_NAME_WITH_TYPE_PATTERN = ":fields";
    protected final String FIELD_VALUES = ":fieldValues";
    private final FieldToStringConverter converterForDB;

    public Database(DataSource dataSource, IDatabase IDatabase) {
        converterForDB = new FieldToStringConverter(IDatabase);
        template = new JdbcTemplate(dataSource);
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

    protected <T> void createTable(String tableName, Class<T> entityClass, String SQL_CREATE_TABLE) {
        try {
            template.execute(insertValues(SQL_CREATE_TABLE, entityClass, null, tableName));
        } catch (SQLException | UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract void save(String table, Object object);

    protected void save(String tableName, Object entity, String SQL_INSERT) {
        try {
            template.execute(insertValues(SQL_INSERT, entity.getClass(), entity, tableName));
        } catch (SQLException | IllegalAccessException | UnknownFieldTypeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract  <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue);

    public <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue, String SQL_SELECT_BY_ID) {
        try {
            return template.queryForObject(insertValues(SQL_SELECT_BY_ID, resultType, null, tableName), getRowMapper(resultType), idValue);
        } catch (SQLException | UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private <T> String insertValues(String sql, Class<T> resultType, Object object, String tableName) throws UnknownFieldTypeException, IllegalAccessException {
        StringBuilder builder = new StringBuilder(sql);
        while (builder.indexOf(TABLE_NAME_PATTERN) != -1) {
            replacePatternOnValue(builder, TABLE_NAME_PATTERN, tableName);
        }
        while (builder.indexOf(FIELD_NAME_PATTERN) != -1) {
            replacePatternOnValue(builder, FIELD_NAME_PATTERN, converterForDB.addValuesInSqlStatement(FieldToStringConverter.InsertType.WITHOUT_TYPE, resultType.getDeclaredFields()));
        }
        while (builder.indexOf(FIELD_NAME_WITH_TYPE_PATTERN) != -1) {
            replacePatternOnValue(builder, FIELD_NAME_WITH_TYPE_PATTERN, converterForDB.addValuesInSqlStatement(FieldToStringConverter.InsertType.WITH_TYPE, resultType.getDeclaredFields()));
        }
        while (builder.indexOf(FIELD_VALUES) != -1) {
            if (object != null) {
                replacePatternOnValue(builder, FIELD_VALUES, converterForDB.getStringObjectValues(resultType.getDeclaredFields(), object));
            }
            else {
                throw new IllegalStateException("object is null");
            }
        }
        return builder.toString();
    }

    private void replacePatternOnValue(StringBuilder builder, String pattern, String value) {
        int startIndex = builder.indexOf(pattern);
        int endIndex = startIndex + pattern.length();
        builder.replace(startIndex, endIndex, value);
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
