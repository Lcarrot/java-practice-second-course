package ru.itis.tyshenko.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class BindingResultMessages {

    public Optional<String> getMessageFromError(BindingResult result, String errorCode) {
        AtomicReference<String> answer = new AtomicReference<>();
        result.getAllErrors()
                .forEach(objectError -> Arrays.stream(Objects.requireNonNull(objectError.getCodes()))
                        .filter(error -> error.equals(errorCode)).findAny()
                        .ifPresentOrElse(error ->
                                answer.set(objectError.getDefaultMessage()), () -> answer.set("")));
        if (answer.get() != null) {
            return Optional.of(answer.get());
        }
        return Optional.empty();
    }
}
