package ru.itis.Tyshenko.converter;

import java.lang.reflect.Field;

public interface DatabaseType {

    String addFieldType(Field field) throws UnknownFieldTypeException;
    String getSeparatorBetweenValueAndType();
    String getSeparatorBetweenValues();
}
