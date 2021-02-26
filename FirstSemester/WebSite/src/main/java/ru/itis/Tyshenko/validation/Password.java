package ru.itis.Tyshenko.validation;

import ru.itis.Tyshenko.validation.validator.PasswordValidator;

import org.w3c.dom.Element;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface Password {

    String message() default "incorrect password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
