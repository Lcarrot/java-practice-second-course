package ru.itis.Tyshenko.util;

import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class BindingResultMessages {

    public static Optional<String> getMessageFromError(BindingResult result, String errorCode) {
        AtomicReference<String> answer = new AtomicReference<>();
        result.getAllErrors()
                .forEach(objectError -> Arrays.stream(Objects.requireNonNull(objectError.getCodes()))
                        .filter(error -> error.equals(errorCode)).findAny().ifPresentOrElse(error -> answer.set(objectError.getDefaultMessage()), () -> answer.set("")));
        if (!answer.get().equals("")) {
            return Optional.of(answer.get());
        }
        return Optional.empty();
    }
}
