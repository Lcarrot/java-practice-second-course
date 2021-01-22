package ru.itis.javalab.filters;


import ru.itis.javalab.entity.User;
import ru.itis.javalab.service.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(servletNames = "authentication", value = "*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;
    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getSession().getAttribute("user") == null) {
            User user;
            Optional<Cookie> cookie = Arrays.stream(request.getCookies()).filter(cook -> cook.getName().equals("auth")).findFirst();
            if (cookie.isPresent()) {
                request.getSession().setAttribute("user", cookie.get().getValue());
                filterChain.doFilter(request, response);
            }
            else {
                response.sendRedirect(context.getContextPath() + "/login");
            }
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
