package ru.itis.tyshenko.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.itis.tyshenko.interceptor.Interceptor;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class WebConfig implements WebMvcConfigurer, BeanPostProcessor {

    private final Map<HandlerInterceptor, Interceptor> interceptorMap = new HashMap<>();

    @Autowired
    private LocalValidatorFactoryBean validatorFactoryBean;

    @Override
    public Validator getValidator() {
        return validatorFactoryBean;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        DefaultMessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
        codesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
        return codesResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptorMap.forEach((HandlerInterceptor key, Interceptor value) -> {
            InterceptorRegistration registration = registry.addInterceptor(key);
            if (value.pathPatterns().length > 0) {
                registration.addPathPatterns(value.pathPatterns());
            }
            if (value.excludePathPatterns().length > 0) {
                registration.excludePathPatterns(value.excludePathPatterns());
            }
            registration.order(value.order());
        });
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        scanInterceptorAnnotation(bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected void scanInterceptorAnnotation(Object bean) {
        Optional<Interceptor> optionalInterceptor = getInterceptorAnnotation(bean.getClass());
        if (optionalInterceptor.isPresent() && bean instanceof HandlerInterceptor) {
            interceptorMap.put((HandlerInterceptor) bean, optionalInterceptor.get());
        }
    }

    private Optional<Interceptor> getInterceptorAnnotation(Class<?> classes) {
        Annotation[] annotations = classes.getAnnotationsByType(Interceptor.class);
        if (annotations.length > 0) {
            return Optional.of(((Interceptor) annotations[0]));
        }
        return Optional.empty();
    }
}
