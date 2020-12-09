package ru.itis.Tyshenko.utility.messages;

public interface PreparerMessage<T> {

    String getMessage(String... messages);
    boolean checkFields();
}
