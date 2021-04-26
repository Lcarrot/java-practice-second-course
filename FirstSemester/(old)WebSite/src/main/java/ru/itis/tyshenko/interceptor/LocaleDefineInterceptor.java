package ru.itis.tyshenko.interceptor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Interceptor(pathPatterns = "/*")
@Profile("master")
public class LocaleDefineInterceptor implements HandlerInterceptor {

    private final LocaleResolver localeResolver;

    private final Environment environment;

    public LocaleDefineInterceptor(LocaleResolver localeResolver, Environment environment) {
        this.localeResolver = localeResolver;
        this.environment = environment;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && Arrays.stream(request.getCookies()).anyMatch(cookie -> cookie.getName().equals("localeInfo"))) {
            return true;
        }
        String ip = request.getRemoteAddr();
        URL url = new URL(environment.getProperty("ip.api.url") + ip);
        Optional<String> locale = getLocaleFromResponseFromUrl(url);
        if (locale.isPresent()) {
            localeResolver.setLocale(request, response, Locale.forLanguageTag(locale.get().toLowerCase(Locale.ROOT)));
        } else {
            String header = request.getHeader("Accept-Language");
            localeResolver.setLocale(request, response, Locale.forLanguageTag(header.toLowerCase(Locale.ROOT)));
        }
        return true;
    }

    private Optional<String> getLocaleFromResponseFromUrl(URL url) {
        String response;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            response = reader.readLine();
            JSONObject object = (JSONObject) new JSONParser().parse(response);
            return Optional.ofNullable(object.get(environment.getProperty("ip.codename")).toString());
        } catch (IOException | ParseException | ClassCastException e) {
            return Optional.empty();
        }
    }
}
