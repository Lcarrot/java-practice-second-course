package ru.itis.tyshenko.exception;

public class ConvertToOtherObjectException extends Exception {
    public ConvertToOtherObjectException(ReflectiveOperationException e) {
        super(e);
    }
}
