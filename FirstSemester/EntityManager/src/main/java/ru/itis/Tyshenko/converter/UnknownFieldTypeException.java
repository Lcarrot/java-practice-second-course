package ru.itis.Tyshenko.converter;

public class UnknownFieldTypeException extends Exception {

    public UnknownFieldTypeException() {
        super("This type field is unsupported");
    }
}
