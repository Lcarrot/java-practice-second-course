package ru.itis.Tyshenko.jdbc;

import ru.itis.Tyshenko.converter.FieldToStringConverter;
import ru.itis.Tyshenko.converter.UnknownFieldTypeException;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Optional;

public class EntityManager {

    private final JdbcTemplate template;
    private final String TABLE_NAME_PATTERN = ":table";
    private final String FIELD_NAME_PATTERN = ":fieldsName";
    private final String FIELD_NAME_WITH_TYPE_PATTERN = ":fields";
    private final String FIELD_VALUES = ":fieldValues";
    private final FieldToStringConverter converterForDB;

    public EntityManager(DataSource dataSource) {
        converterForDB = new FieldToStringConverter(",", " ");
        template = new JdbcTemplate(dataSource);
        SQL_CREATE_TABLE = createSourceSqlStatement("create table ", TABLE_NAME_PATTERN, " (", FIELD_NAME_WITH_TYPE_PATTERN, ")");
        SQL_INSERT = createSourceSqlStatement("insert into", TABLE_NAME_PATTERN, " (", FIELD_NAME_PATTERN, ") ", "values", " (", FIELD_VALUES, ")");
        SQL_SELECT_BY_ID = createSourceSqlStatement("select ", FIELD_NAME_PATTERN, " from", TABLE_NAME_PATTERN, "where id = ?");
    }

    private String createSourceSqlStatement(String... parts) {
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(part).append(" ");
        }
        return builder.append(";").toString();
    }

    private final String SQL_CREATE_TABLE;

    public <T> void createTable(String tableName, Class<T> entityClass) {
        try {
            converterForDB.setFields(entityClass.getDeclaredFields());
            template.execute(insertValues(SQL_CREATE_TABLE, tableName));
        } catch (SQLException | UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final String SQL_INSERT;

    public void save(String tableName, Object entity) {
        try {
            converterForDB.setObject(entity);
            template.execute(insertValues(SQL_INSERT, tableName));
        } catch (SQLException | IllegalAccessException | UnknownFieldTypeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final String SQL_SELECT_BY_ID;

    public <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        try {
            converterForDB.setFields(resultType.getDeclaredFields());
            return template.queryForObject(insertValues(SQL_SELECT_BY_ID, tableName), getRowMapper(resultType), idValue);
        } catch (SQLException | UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    //Блок методов низкого уровня абстракции(получение полей и т.д.)

    private String insertValues(String sql, String tableName) throws UnknownFieldTypeException, IllegalAccessException {
        StringBuilder builder = new StringBuilder(sql);
        while (builder.indexOf(TABLE_NAME_PATTERN) != -1) {
            replacePatternOnValue(builder, TABLE_NAME_PATTERN, tableName);
        }
        while (builder.indexOf(FIELD_NAME_PATTERN) != -1) {
            replacePatternOnValue(builder, FIELD_NAME_PATTERN, converterForDB.getStringFieldNames());
        }
        while (builder.indexOf(FIELD_NAME_WITH_TYPE_PATTERN) != -1) {
            replacePatternOnValue(builder, FIELD_NAME_WITH_TYPE_PATTERN, converterForDB.getStringFieldsWithTypes());
        }
        while (builder.indexOf(FIELD_VALUES) != -1) {
            replacePatternOnValue(builder, FIELD_VALUES, converterForDB.getStringObjectValues());
        }
        return builder.toString();
    }

    private void replacePatternOnValue(StringBuilder builder, String pattern, String value) {
        int startIndex = builder.indexOf(pattern);
        int endIndex = startIndex + pattern.length();
        builder.replace(startIndex, endIndex, value);
    }

    private <T> RowMapper<T> getRowMapper(Class<T> resultType) {
        return result -> {
            T object;
            try {
                object = resultType.newInstance();
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(object, result.getObject(field.getName()));
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
            return object;
        };
    }
}
