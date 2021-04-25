package ru.itis.tyshenko.interceptor;

import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import ru.itis.tyshenko.util.JsonParser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

//@Interceptor
@Profile("master")
public class LocaleDefineInterceptor implements HandlerInterceptor {

    private final LocaleResolver localeResolver;

    private final Environment environment;

    private final JsonParser jsonParser;

    public LocaleDefineInterceptor(LocaleResolver localeResolver, Environment environment, JsonParser jsonParser) {
        this.localeResolver = localeResolver;
        this.environment = environment;
        this.jsonParser = jsonParser;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && Arrays.stream(request.getCookies()).anyMatch(cookie -> cookie.getName().equals("localeInfo"))) {
            return true;
        }
        String ip = request.getRemoteAddr();
        Optional<String> locale = jsonParser.getPropertyFromInputStream(new URL
                (environment.getProperty("ip.api.url") + ip).openStream(),
                environment.getProperty("ip.codename"));
        if (locale.isPresent()) {
            localeResolver.setLocale(request, response, Locale.forLanguageTag(locale.get().toLowerCase(Locale.ROOT)));
        } else {
            String header = request.getHeader("Accept-Language");
            localeResolver.setLocale(request, response, Locale.forLanguageTag(header.toLowerCase(Locale.ROOT)));
        }
        return true;
    }
}
