package ru.itis.Tyshenko.interceptor;

import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Interceptor(pathPatterns = "/*")
public class MyLocaleChangeInterceptor extends LocaleChangeInterceptor {

    public MyLocaleChangeInterceptor() {
        super();
        setParamName("lang");
    }
}
