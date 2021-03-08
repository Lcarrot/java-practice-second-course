package ru.itis.tyshenko.util;

import org.springframework.stereotype.Component;
import ru.itis.tyshenko.exception.ConvertToOtherObjectException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Component
public class ReflectionConverter {

    public <From, To> To convertToOtherObjectWithSpecialAnnotation(From from, Class<To> toClass,
                                                                   Class<? extends Annotation> annotationClass)
            throws ConvertToOtherObjectException {
        Field[] fields = toClass.getDeclaredFields();
        To object;
        try {
            object = toClass.getConstructor().newInstance();
            for (Field field : fields) {
                Field fromField;
                try {
                    fromField = from.getClass().getDeclaredField(field.getName());
                    fromField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    continue;
                }
                if (fromField.getAnnotation(annotationClass) != null) {
                    field.setAccessible(true);
                    field.set(object, fromField.get(from));
                }
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException
                | IllegalAccessException e) {
            throw new ConvertToOtherObjectException(e);
        }
        return object;
    }

    public <From, To> To convertToOtherObject(From from, Class<To> toClass)
            throws ConvertToOtherObjectException {
        Field[] fields = toClass.getDeclaredFields();
        To object;
        try {
            object = toClass.getConstructor().newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                Field fieldObject;
                try {
                    fieldObject = from.getClass().getDeclaredField(field.getName());
                    fieldObject.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    continue;
                }
                field.setAccessible(true);
                field.set(object, fieldObject.get(from));
            }
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException
                | IllegalAccessException e) {
            throw new ConvertToOtherObjectException(e);
        }
        return object;
    }
}
