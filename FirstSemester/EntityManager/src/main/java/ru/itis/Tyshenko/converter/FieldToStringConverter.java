package ru.itis.Tyshenko.converter;

import java.lang.reflect.Field;

public class FieldToStringConverter {

    private Field[] fields;
    private Object object;
    private String separatorBetweenFields;
    private String separatorBetweenTypeAndValue;

    public FieldToStringConverter(String separatorBetweenFields, String separatorBetweenTypeAndValue){
        this.separatorBetweenFields = separatorBetweenFields;
        this.separatorBetweenTypeAndValue = separatorBetweenTypeAndValue;
    }

    public FieldToStringConverter(Object object, String separatorBetweenFields, String separatorBetweenTypeAndValue) {
        this.object = object;
        this.fields = object.getClass().getDeclaredFields();
        this.separatorBetweenFields = separatorBetweenFields;
        this.separatorBetweenTypeAndValue = separatorBetweenTypeAndValue;
    }

    public FieldToStringConverter(Field[] fields, String separatorBetweenFields, String separatorBetweenTypeAndValue) {
        this.fields = fields;
        this.separatorBetweenFields = separatorBetweenFields;
        this.separatorBetweenTypeAndValue = separatorBetweenTypeAndValue;
    }

    public String getStringFieldNames() {
        StringBuilder fieldsString = new StringBuilder();
        for (int i = 0; i < fields.length - 1; i++) {
            addFieldName(fieldsString, fields[i]);
            fieldsString.append(separatorBetweenFields);
        }
        addFieldName(fieldsString, fields[fields.length - 1]);
        return fieldsString.toString();
    }

    public String getStringFieldsWithTypes() throws UnknownFieldTypeException {
        StringBuilder buildNameWithTypes = new StringBuilder();
        for (int i = 0; i < fields.length - 1; i++) {
            addFieldWithType(buildNameWithTypes, fields[i]);
            buildNameWithTypes.append(separatorBetweenFields);
        }
        addFieldWithType(buildNameWithTypes, fields[fields.length - 1]);
        return buildNameWithTypes.toString();
    }

    public String getStringObjectValues() throws IllegalAccessException {
        StringBuilder valuesString = new StringBuilder();
        for (int i = 0; i < fields.length - 1; i++) {
            addValue(valuesString, fields[i]);
            valuesString.append(separatorBetweenFields);
        }
        addValue(valuesString, fields[fields.length - 1]);
        return valuesString.toString();
    }

    private void addFieldWithType(StringBuilder builder, Field field) throws UnknownFieldTypeException {
        addFieldName(builder, field);
        builder.append(separatorBetweenTypeAndValue);
        addFieldType(builder, field);
    }

    private void addFieldName(StringBuilder builder, Field field) {
        builder.append(field.getName());
    }

    private void addFieldType(StringBuilder builder, Field field) throws UnknownFieldTypeException {
        String type;
        if (Long.class.equals(field.getType())) {
            type = "bigint";
        } else if (Integer.class.equals(field.getType())) {
            type = "int";
        } else if (String.class.equals(field.getType()) || Character.class.equals(field.getType())) {
            type = "varchar";
        } else if (Boolean.class.equals(field.getType())) {
            type = "boolean";
        }
        else {
            throw new UnknownFieldTypeException();
        }
        builder.append(type);
    }

    private void addValue(StringBuilder builder, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        builder.append("'").append(field.get(object)).append("'");
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
        object = null;
    }

    public void setObject(Object object) {
        this.object = object;
        this.fields = object.getClass().getDeclaredFields();
    }

    public void setSeparatorBetweenFields(String separatorBetweenFields) {
        this.separatorBetweenFields = separatorBetweenFields;
    }

    public void setSeparatorBetweenTypeAndValue(String separatorBetweenTypeAndValue) {
        this.separatorBetweenTypeAndValue = separatorBetweenTypeAndValue;
    }

    public Field[] getFields() {
        return fields;
    }

    public Object getObject() {
        return object;
    }

    public String getSeparatorBetweenFields() {
        return separatorBetweenFields;
    }

    public String getSeparatorBetweenTypeAndValue() {
        return separatorBetweenTypeAndValue;
    }
}
