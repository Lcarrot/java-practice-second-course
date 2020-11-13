package ru.itis.Tyshenko.jdbc;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Optional;

public class EntityManager {

    private final JdbcTemplate template;
    private final String TABLE_NAME_PATTERN = "table";
    private final String FIELD_NAME_PATTERN = "fieldsName";
    private final String FIELD_NAME_WITH_TYPE_PATTERN = "fields";
    private final String FIELD_VALUES = "fieldValues";

    public EntityManager(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
        SQL_CREATE_TABLE = createSourceSqlStatement("create table ",TABLE_NAME_PATTERN," (",FIELD_NAME_WITH_TYPE_PATTERN,");");
        SQL_INSERT = createSourceSqlStatement("insert into", TABLE_NAME_PATTERN," (" ,FIELD_NAME_PATTERN , ") ", "values", " (", FIELD_VALUES, ");");
        SQL_SELECT_BY_ID = createSourceSqlStatement("select ",FIELD_NAME_PATTERN," from", TABLE_NAME_PATTERN, "where id = ?");
    }

    private String createSourceSqlStatement(String ... parts) {
        StringBuilder builder = new StringBuilder();
        for (String part: parts) {
            builder.append(part).append(" ");
        }
        return builder.append(";").toString();
    }

    //language = SQL
    private final String SQL_CREATE_TABLE;
    public <T> void createTable(String tableName, Class<T> entityClass) {
        StringBuilder builder = new StringBuilder();
        if (SQL_CREATE_TABLE.contains(TABLE_NAME_PATTERN)) {
            replacePatternOnValue(builder, TABLE_NAME_PATTERN, tableName);
        }
        if (SQL_CREATE_TABLE.contains(FIELD_NAME_WITH_TYPE_PATTERN)) {
            replacePatternOnValue(builder, FIELD_NAME_WITH_TYPE_PATTERN, getStringFieldsWithTypes(entityClass.getDeclaredFields()));
        }
        try {
            if (template.execute(builder.toString())) {
                throw new IllegalStateException("Statement don't execute");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final String SQL_INSERT;
    public void save(String tableName, Object entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        if (SQL_CREATE_TABLE.contains(TABLE_NAME_PATTERN)) {
            replacePatternOnValue(builder, TABLE_NAME_PATTERN, tableName);
        }
        if (SQL_INSERT.contains(FIELD_NAME_PATTERN)) {
            replacePatternOnValue(builder, FIELD_NAME_PATTERN, getStringFields(fields));
        }
        if (SQL_INSERT.contains(FIELD_VALUES)) {
            try {
                replacePatternOnValue(builder, FIELD_VALUES, getStringValues(fields, entity));
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }
        try {
            if (template.execute(builder.toString())) {
                throw new IllegalStateException("Statement don't execute");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final String SQL_SELECT_BY_ID;
    public <T, ID> Optional<T> findById(String tableName, Class<T> resultType, ID idValue) {
        Field[] fields = resultType.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        if (SQL_SELECT_BY_ID.contains(TABLE_NAME_PATTERN)) {
            replacePatternOnValue(builder, TABLE_NAME_PATTERN, tableName);
        }
        if (SQL_SELECT_BY_ID.contains(FIELD_NAME_PATTERN)) {
            replacePatternOnValue(builder, FIELD_NAME_PATTERN, getStringFields(fields));
        }
        RowMapper<T> rowMapper = getRowMapper(resultType);
        try {
            return template.queryForObject(SQL_SELECT_BY_ID, rowMapper, idValue);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }


    //Блок для методов низкого уровня абстракции(получение полей и т.д.)

    private String getStringFieldsWithTypes(Field[] fields) {
        StringBuilder buildNameWithTypes = new StringBuilder();
        for (int i = 0; i < fields.length - 1; i++) {
            addFieldWithType(buildNameWithTypes, fields[i]);
            buildNameWithTypes.append(",\n");
        }
        addFieldWithType(buildNameWithTypes, fields[fields.length - 1]);
        return buildNameWithTypes.toString();
    }

    private <T> RowMapper<T> getRowMapper(Class<T> resultType) {
        return result -> {
            T object;
            try {
                Constructor<T> constructor = resultType.getConstructor();
                object = constructor.newInstance();
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(field.getType(), result.getObject(field.getType().getSimpleName()));
                }
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                throw new IllegalArgumentException(e);
            }
            return object;
        };
    }

    private void addFieldWithType(StringBuilder builder, Field field) {
        addFieldName(builder, field);
        builder.append(" ");
        addFieldType(builder, field);
    }

    private void addFieldName(StringBuilder builder, Field field) {
        builder.append("'").append(field.getName()).append("'");
    }

    private void addFieldType(StringBuilder builder, Field field) {
        builder.append(field.getType().getSimpleName().toLowerCase());
    }

    private String getStringFields(Field[] fields) {
       StringBuilder fieldsString = new StringBuilder();
       for (int i = 0; i < fields.length - 1; i++) {
           addFieldName(fieldsString, fields[i]);
           fieldsString.append(",");
       }
       addFieldName(fieldsString, fields[fields.length - 1]);
       return fieldsString.toString();
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

    private void addValue(StringBuilder builder, Field field, Object object) throws IllegalAccessException {
        builder.append("'").append(field.get(object)).append("'");
    }

    private void replacePatternOnValue(StringBuilder builder, String pattern, String value) {
        int startIndex = builder.indexOf(pattern);
        int endIndex = builder.lastIndexOf(pattern);
        builder.replace(startIndex, endIndex, value);
    }
}
