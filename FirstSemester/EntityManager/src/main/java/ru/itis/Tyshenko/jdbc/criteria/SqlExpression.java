package ru.itis.Tyshenko.jdbc.criteria;

import ru.itis.Tyshenko.converter.FieldToStringConverter;
import ru.itis.Tyshenko.converter.IDatabase;
import ru.itis.Tyshenko.converter.UnknownFieldTypeException;
import ru.itis.Tyshenko.jdbc.database.SqlReplacer;

public class SqlExpression {

    private final StringBuilder sql;
    protected final String TABLE_NAME_PATTERN = ":table";
    protected final String FIELD_NAME_PATTERN = ":fieldsName";
    protected final String FIELD_NAME_WITH_TYPE_PATTERN = ":fields";
    protected final String FIELD_VALUES = ":fieldValues";

    public SqlExpression(Object entity, String tableName, IDatabase database) {
        FieldToStringConverter fieldToStringConverter = new FieldToStringConverter(database);
        SqlReplacer sqlReplacer = new SqlReplacer(TABLE_NAME_PATTERN,
                FIELD_NAME_PATTERN, FIELD_NAME_WITH_TYPE_PATTERN, FIELD_VALUES, fieldToStringConverter);
        try {
            String SQL_SELECT_BY_ID = "select " + FIELD_NAME_PATTERN + " from " + TABLE_NAME_PATTERN + " where ";
            sql = new StringBuilder(sqlReplacer.insertValues(SQL_SELECT_BY_ID, entity.getClass(), entity, tableName));
        } catch (UnknownFieldTypeException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public void addCondition(String condition) {
        sql.append(condition);
    }

    public String getSqlString() {
        return sql.toString();
    }
}
