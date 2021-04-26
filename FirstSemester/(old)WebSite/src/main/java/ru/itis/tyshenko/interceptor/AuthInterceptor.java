package ru.itis.tyshenko.interceptor;


import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Interceptor(pathPatterns = "/service/*")
@Profile("master")
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getSession().getAttribute("user") != null) {
            return true;
        }
        response.setStatus(401);
        return false;
    }
}
