package ru.itis.javalab.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class CsrfFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getMethod().equals("GET")) {
//            System.out.println(request.getAttribute("_csrf_token"));
            doGet(request, response, filterChain);
        }
        else {
            doPost(request, response, filterChain);
        }
    }

    private void doGet(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String csrf = UUID.randomUUID().toString();
        request.setAttribute("_csrf_token", csrf);
        request.getSession().setAttribute("_csrf_token", csrf);
        chain.doFilter(request, response);
    }

    private void doPost(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestCsrf = request.getParameter("_csrf_token");
        String sessionCsrf = (String) request.getSession(false).getAttribute("_csrf_token");
        if (requestCsrf.equals(sessionCsrf)) {
            chain.doFilter(request, response);
        }
        else {
            response.setStatus(403);
        }
    }

    @Override
    public void destroy() {

    }
}
