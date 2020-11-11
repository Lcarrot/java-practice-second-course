package ru.itis.Tyshenko.jdbc;

import javax.sql.DataSource;
import java.lang.reflect.Field;

public class EntityManager {

    private final DataSource dataSource;
    private final String TABLE_NAME_PATTERN = "table";
    private final String FIELD_NAME_PATTERN = "fieldsName";
    private final String FIELD_NAME_WITH_TYPE_PATTERN = "fields";

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
        StringBuilder builder = new StringBuilder();
        //language=SQL
        SQL_CREATE_TABLE = createSourceSqlStatement("create table ",TABLE_NAME_PATTERN," (",FIELD_NAME_WITH_TYPE_PATTERN,")");
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
        String sql = createSQLStatement(SQL_CREATE_TABLE, tableName, getFields(entityClass));
    }

    public void save(String tableName, Object entity) {
        Class<?> classOfEntity = entity.getClass();
        // сканируем его поля
        // сканируем значения этих полей
        // генерируем insert into
    }

    // User user = entityManager.findById("account", User.class, Long.class, 10L);
    public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        // сгенеририровать select
        return null;
    }

    private String createSQLStatement(String sql,String tableName, Field[] fields) {
        StringBuilder builder = new StringBuilder(sql);
        while (sql.contains(TABLE_NAME_PATTERN)) {
            replacePatternOnValue(builder, TABLE_NAME_PATTERN, tableName);
        }
        boolean bool;
        while ((bool =sql.contains(FIELD_NAME_WITH_TYPE_PATTERN)) || sql.contains(FIELD_NAME_PATTERN)) {
            replacePatternOnFields(builder, fields, bool);
        }
        return builder.toString();
    }

    private <T> Field[] getFields(Class<T> entityClass) {
        return entityClass.getDeclaredFields();
    }

    private void replacePatternOnFields(StringBuilder builder, Field[] fields, boolean needTypes) {
        if (needTypes) {
            replacePatternOnFieldsWithTypes(builder, fields);
        }
        else {
            replacePatternOnFieldsWithoutTypes(builder, fields);
        }
    }

    private void replacePatternOnFieldsWithTypes(StringBuilder builder, Field[] fields) {
        // TODO: 11/12/2020 вставлять имя поля с его типом
    }

    private void replacePatternOnFieldsWithoutTypes(StringBuilder builder, Field[] fields) {
        // // TODO: 11/12/2020 вставлять имя поля без типа
    }

    private void replacePatternOnValue(StringBuilder builder, String pattern, String value) {
        int startIndex = builder.indexOf(pattern);
        int endIndex = builder.lastIndexOf(pattern);
        builder.replace(startIndex, endIndex, value);
    }
}
