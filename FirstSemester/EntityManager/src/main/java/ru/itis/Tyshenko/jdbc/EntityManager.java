package ru.itis.Tyshenko.jdbc;

import ru.itis.Tyshenko.converter.DataBase;
import ru.itis.Tyshenko.converter.FieldToStringConverter;
import ru.itis.Tyshenko.converter.UnknownFieldTypeException;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class EntityManager {

    private final JdbcTemplate template;
    private final String TABLE_NAME_PATTERN = ":table";
    private final String FIELD_NAME_PATTERN = ":fieldsName";
    private final String FIELD_NAME_WITH_TYPE_PATTERN = ":fields";
    private final String FIELD_VALUES = ":fieldValues";
    private final FieldToStringConverter converterForDB;

    public EntityManager(DataSource dataSource,DataBase dataBase) {
        converterForDB = new FieldToStringConverter(dataBase);
        template = new JdbcTemplate(dataSource);
    }

    private String SQL_CREATE_TABLE = "create table " + TABLE_NAME_PATTERN + " (" + FIELD_NAME_WITH_TYPE_PATTERN + ")";

    public <T> void createTable(String tableName, Class<T> entityClass) {
        try {
            template.execute(insertValues(SQL_CREATE_TABLE, entityClass, null, tableName));
        } catch (SQLException | UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final String SQL_INSERT = "insert into " + TABLE_NAME_PATTERN + " (" + FIELD_NAME_PATTERN + ") " + "values" + " (" + FIELD_VALUES + ")";

    public void save(String tableName, Object entity) {
        try {
            template.execute(insertValues(SQL_INSERT,entity.getClass(), entity, tableName));
        } catch (SQLException | IllegalAccessException | UnknownFieldTypeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private final String SQL_SELECT_BY_ID = "select " + FIELD_NAME_PATTERN + " from " + TABLE_NAME_PATTERN + " where id = ?";

    public <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        try {
            return template.queryForObject(insertValues(SQL_SELECT_BY_ID, resultType, null, tableName), getRowMapper(resultType), idValue);
        } catch (SQLException | UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    //Блок методов низкого уровня абстракции(получение полей и т.д.)

    private <T> String insertValues(String sql, Class<T> resultType, Object object, String tableName) throws UnknownFieldTypeException, IllegalAccessException {
        StringBuilder builder = new StringBuilder(sql);
        while (builder.indexOf(TABLE_NAME_PATTERN) != -1) {
            replacePatternOnValue(builder, TABLE_NAME_PATTERN, tableName);
        }
        while (builder.indexOf(FIELD_NAME_PATTERN) != -1) {
            replacePatternOnValue(builder, FIELD_NAME_PATTERN, converterForDB.addValuesInSqlStatement(false, resultType.getDeclaredFields()));
        }
        while (builder.indexOf(FIELD_NAME_WITH_TYPE_PATTERN) != -1) {
            replacePatternOnValue(builder, FIELD_NAME_WITH_TYPE_PATTERN, converterForDB.addValuesInSqlStatement(true, resultType.getDeclaredFields()));
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

    private <T> RowMapper<T> getRowMapper(Class<T> resultType) {
        return result -> {
            T object;
            try {
                object = resultType.getConstructor().newInstance();
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(object, result.getObject(field.getName()));
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
            return object;
        };
    }
}
