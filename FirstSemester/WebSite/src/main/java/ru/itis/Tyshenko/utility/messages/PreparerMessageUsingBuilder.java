package ru.itis.Tyshenko.utility.messages;

import java.util.List;
import java.util.Map;

public abstract class PreparerMessageUsingBuilder<T> implements PreparerMessage<T> {

    protected StringBuilder concatAllErrorsSignUpStatus(List<T> statuses, Map<T, String> statusDescription) {
        StringBuilder builder = new StringBuilder();
        int i = 1;
        for (T status : statuses) {
            builder.append(i++).append(statusDescription.get(status)).append("\n");
        }
        return builder;
    }
}
