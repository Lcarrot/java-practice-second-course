package ru.itis.javalab.filters;


import ru.itis.javalab.service.UsersService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class AuthenticationFilter implements Filter {

    private ServletContext context;
    private UsersService userService;

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
        userService = (UsersService) context.getAttribute("userService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getSession().getAttribute("user") == null) {
            Optional<Cookie> cookie;
            if (request.getCookies() != null &&
                    (cookie = Arrays.stream(request.getCookies()).filter(cook -> cook.getName().equals("auth")).findFirst()).isPresent()) {
                request.getSession().setAttribute("user", userService.getUserByAuth(cookie.get().getValue()));
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(context.getContextPath() + "/login");
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
