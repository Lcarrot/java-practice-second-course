package ru.itis.Tyshenko.converter;

import java.lang.reflect.Field;

public class PostgreSQL implements DataBase {

    public String addFieldType(Field field) throws UnknownFieldTypeException {
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
        return type;
    }

    @Override
    public String getSeparatorBetweenValueAndType() {
        return " ";
    }

    @Override
    public String getSeparatorBetweenValues() {
        return ", ";
    }
}
