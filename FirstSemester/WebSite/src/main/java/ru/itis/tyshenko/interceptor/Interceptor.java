package ru.itis.tyshenko.interceptor;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Interceptor {
    @AliasFor(annotation = Component.class)
    String value() default "";
    String[] pathPatterns() default {};
    String[] excludePathPatterns() default {};
    int order() default 0;
}
