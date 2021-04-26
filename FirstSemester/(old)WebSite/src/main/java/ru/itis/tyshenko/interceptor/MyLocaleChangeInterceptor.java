package ru.itis.tyshenko.interceptor;

import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Interceptor(pathPatterns = "/*", order = 1)
public class MyLocaleChangeInterceptor extends LocaleChangeInterceptor {

    public MyLocaleChangeInterceptor() {
        super();
        setParamName("lang");
    }
}
