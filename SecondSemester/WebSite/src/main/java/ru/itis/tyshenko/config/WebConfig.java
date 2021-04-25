package ru.itis.tyshenko.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.itis.tyshenko.interceptor.Interceptor;
import ru.itis.tyshenko.util.AnnotationRegister;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LocalValidatorFactoryBean validatorFactoryBean;

    @Autowired
    private AnnotationRegister register;

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
        register.getBeansByAnnotationWithData(HandlerInterceptor.class, Interceptor.class)
                .forEach((HandlerInterceptor key, Interceptor value) ->
                        registry.addInterceptor(key)
                        .addPathPatterns(value.pathPatterns())
                        .excludePathPatterns(value.excludePathPatterns())
                        .order(value.order()));
    }
}