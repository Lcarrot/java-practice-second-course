package ru.itis.Tyshenko.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Interceptor(pathPatterns = "/*")
public class IpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        String apiURL = "http://ipwhois.app/json/";
        String codePattern = "country_code";
        URL url = new URL(apiURL+ip);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        System.out.println(ip);
        while ((line = reader.readLine()) != null) {
            if (line.contains(codePattern)) {
                System.out.println(line);
                break;
            }
        }
        reader.close();
        return true;
    }
}
