package ru.itis.tyshenko.validation;

import ru.itis.tyshenko.validation.validator.UnrepeatableFieldsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UnrepeatableFieldsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnrepeatableFields {
    String message() default "repeatable fields";
    String[] fields() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
