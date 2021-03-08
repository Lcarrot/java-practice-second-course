package ru.itis.tyshenko.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Interceptor(pathPatterns = "/*")
public class CsrfInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("GET")) {
            return doGet(request);
        }
        else if (request.getMethod().equals("POST")) {
            return doPost(request);
        }
        return false;

    }

    private boolean doGet(HttpServletRequest request) {
        String csrf = UUID.randomUUID().toString();
        request.setAttribute("_csrf_token", csrf);
        request.getSession().setAttribute("_csrf_token", csrf);
        return true;
    }

    private boolean doPost(HttpServletRequest request) {
        String requestCsrf = request.getParameter("_csrf_token");
        String sessionCsrf = (String) request.getSession(false).getAttribute("_csrf_token");
        return requestCsrf.equals(sessionCsrf);
    }
}
