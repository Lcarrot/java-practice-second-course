package ru.itis.Tyshenko.jdbc.database;

import ru.itis.Tyshenko.converter.FieldToStringConverter;
import ru.itis.Tyshenko.converter.UnknownFieldTypeException;

public class SqlReplacer {

    private String TABLE_NAME_PATTERN;
    private String FIELD_NAME_PATTERN;
    private String FIELD_NAME_WITH_TYPE_PATTERN;
    private String FIELD_VALUES;
    private FieldToStringConverter converterForDB;

    public SqlReplacer(String TABLE_NAME_PATTERN, String FIELD_NAME_PATTERN, String FIELD_NAME_WITH_TYPE_PATTERN, String FIELD_VALUES, FieldToStringConverter converterForDB) {
        this.TABLE_NAME_PATTERN = TABLE_NAME_PATTERN;
        this.FIELD_NAME_PATTERN = FIELD_NAME_PATTERN;
        this.FIELD_NAME_WITH_TYPE_PATTERN = FIELD_NAME_WITH_TYPE_PATTERN;
        this.FIELD_VALUES = FIELD_VALUES;
        this.converterForDB = converterForDB;
    }

    public  <T> String insertValues(String sql, Class<T> resultType, Object object, String tableName) throws UnknownFieldTypeException, IllegalAccessException {
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
}
