package ru.itis.Tyshenko.converter;

import java.lang.reflect.Field;

public interface IDatabase {

    String addFieldType(Field field) throws UnknownFieldTypeException;
    String getSeparatorBetweenValueAndType();
    String getSeparatorBetweenValues();
}
