package com.example.demo.util;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Component
public class GenerateResponse {

    public ResponseEntity<?> generateByOptionalPresent(Object executor,
                                                       String methodName, Object ... attributes) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return ((Optional<?>) executor.getClass()
                .getMethod(methodName, getClasses(attributes))
                .invoke(executor, attributes))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    private Class<?>[] getClasses(Object ... attributes) {
        Class<?>[] classes = new Class[attributes.length];
        for (int i = 0; i < attributes.length; i++) classes[i] = attributes[i].getClass();
        return classes;
    }
}
