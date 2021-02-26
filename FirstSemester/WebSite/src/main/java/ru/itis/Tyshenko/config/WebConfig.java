package ru.itis.Tyshenko.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import ru.itis.Tyshenko.interceptor.AuthInterceptor;
import ru.itis.Tyshenko.interceptor.CsrfInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LocalValidatorFactoryBean validatorFactoryBean;

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;

    @Override
    public Validator getValidator() {
        return validatorFactoryBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CsrfInterceptor()).addPathPatterns("/*").order(1);
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/profile").order(2);
        registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/*");
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        DefaultMessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
        codesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
        return codesResolver;
    }
}
