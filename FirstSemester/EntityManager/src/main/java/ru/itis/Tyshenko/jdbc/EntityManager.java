package ru.itis.Tyshenko.jdbc;

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

    public EntityManager(DataSource dataSource) {
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
            template.execute(insertValues(SQL_CREATE_TABLE, tableName, entityClass.getDeclaredFields()));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final String SQL_INSERT;

    public void save(String tableName, Object entity) {
        try {
            template.execute(insertValues(SQL_INSERT,tableName, entity.getClass().getDeclaredFields(), entity));
        } catch (SQLException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final String SQL_SELECT_BY_ID;

    public <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        RowMapper<T> rowMapper = getRowMapper(resultType);
        try {
            return template.queryForObject(insertValues(SQL_SELECT_BY_ID, tableName, resultType.getDeclaredFields()), rowMapper, idValue);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    //Блок методов низкого уровня абстракции(получение полей и т.д.)

    private String insertValues(String sql, String tableName, Field[] fields, Object entity) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder(insertValues(sql, tableName, fields));
        if (sql.contains(FIELD_VALUES)) {
            replacePatternOnValue(builder, FIELD_VALUES, getStringValues(fields, entity));
        }
        return builder.toString();
    }

    private String insertValues(String sql, String tableName, Field[] fields) {
        StringBuilder builder = new StringBuilder(sql);
        if (sql.contains(TABLE_NAME_PATTERN)) {
            replacePatternOnValue(builder, TABLE_NAME_PATTERN, tableName);
        }
        if (sql.contains(FIELD_NAME_PATTERN)) {
            replacePatternOnValue(builder, FIELD_NAME_PATTERN, getStringFieldNames(fields));
        }
        if (sql.contains(FIELD_NAME_WITH_TYPE_PATTERN)) {
            replacePatternOnValue(builder, FIELD_NAME_WITH_TYPE_PATTERN, getStringFieldsWithTypes(fields));
        }
        return builder.toString();
    }

    private void replacePatternOnValue(StringBuilder builder, String pattern, String value) {
        int startIndex = builder.indexOf(pattern);
        int endIndex = startIndex + pattern.length();
        builder.replace(startIndex, endIndex, value);
    }

    private String getStringFieldNames(Field[] fields) {
        StringBuilder fieldsString = new StringBuilder();
        for (int i = 0; i < fields.length - 1; i++) {
            addFieldName(fieldsString, fields[i]);
            fieldsString.append(",");
        }
        addFieldName(fieldsString, fields[fields.length - 1]);
        return fieldsString.toString();
    }

    private String getStringFieldsWithTypes(Field[] fields) {
        StringBuilder buildNameWithTypes = new StringBuilder();
        for (int i = 0; i < fields.length - 1; i++) {
            addFieldWithType(buildNameWithTypes, fields[i]);
            buildNameWithTypes.append(",\n");
        }
        addFieldWithType(buildNameWithTypes, fields[fields.length - 1]);
        return buildNameWithTypes.toString();
    }

    private String getStringValues(Field[] fields, Object object) throws IllegalAccessException {
        StringBuilder valuesString = new StringBuilder();
        for (int i = 0; i < fields.length - 1; i++) {
            addValue(valuesString, fields[i], object);
            valuesString.append(",");
        }
        addValue(valuesString, fields[fields.length - 1], object);
        return valuesString.toString();
    }

    private void addFieldWithType(StringBuilder builder, Field field) {
        addFieldName(builder, field);
        builder.append(" ");
        addFieldType(builder, field);
    }

    private void addFieldName(StringBuilder builder, Field field) {
        builder.append(field.getName());
    }

    private void addFieldType(StringBuilder builder, Field field) {
        String type = "";
        if (Long.class.equals(field.getType())) {
            type = "bigint";
        } else if (Integer.class.equals(field.getType())) {
            type = "int";
        } else if (String.class.equals(field.getType()) || Character.class.equals(field.getType())) {
            type = "varchar";
        } else if (Boolean.class.equals(field.getType())) {
            type = "boolean";
        }
        builder.append(type);
    }

    private void addValue(StringBuilder builder, Field field, Object object) throws IllegalAccessException {
        field.setAccessible(true);
        builder.append("'").append(field.get(object)).append("'");
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
