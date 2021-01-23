package ru.itis.Tyshenko.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getSession().getAttribute("user") != null) {
            return true;
        }
        else {
            response.setStatus(403);
        }
        return true;
    }
}
