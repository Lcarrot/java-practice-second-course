package ru.itis.tyshenko.interceptor;

import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Interceptor(pathPatterns = "/admin/*", excludePathPatterns = "/admin/signIn")
@Profile("master")
public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return request.getSession(false).getAttribute("admin") != null;
    }
}
