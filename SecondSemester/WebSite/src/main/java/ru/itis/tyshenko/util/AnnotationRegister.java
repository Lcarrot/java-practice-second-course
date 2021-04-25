package ru.itis.tyshenko.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AnnotationRegister {

    @Autowired
    private ApplicationContext applicationContext;

    public <T, V extends Annotation> Map<T,V> getBeansByAnnotationWithData(Class<T> objClass, Class<V> annClass) {
        Map<T, V> map = new LinkedHashMap<>();
        applicationContext.getBeansOfType(objClass)
                .forEach((name, bean) -> scanAnnotation(name, bean, annClass)
                .ifPresent(ann -> map.put(bean,ann)));
        return map;
    }

    private <V extends Annotation> Optional<V> scanAnnotation(String name, Object bean, Class<V> annClass) {
       Optional<V> ann = scanAnnotationAsComponent(bean, annClass);
        if (ann.isPresent()) return ann;
        return scanAnnotationAsBean(name, annClass);
    }

    private <V extends Annotation> Optional<V> scanAnnotationAsComponent(Object bean, Class<V> annClass) {
       return Arrays.stream(bean.getClass()
                .getAnnotationsByType(annClass))
                .findAny();
    }

    private <V extends Annotation> Optional<V> scanAnnotationAsBean(String name, Class<V> annClass) {
        return Optional.ofNullable(applicationContext.findAnnotationOnBean(name, annClass));
    }
}
