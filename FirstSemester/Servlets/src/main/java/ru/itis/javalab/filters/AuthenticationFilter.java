package ru.itis.javalab.filters;

import ru.itis.javalab.entity.User;
import ru.itis.javalab.service.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@WebFilter(servletNames = "authentication", value = "*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;
    private UsersService usersService;
    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
        this.usersService = (UsersService) context.getAttribute("userService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        List<User> users = null;
        if (cookies != null) {
            users = findUsersByAuthAndCookie(cookies);
        }
        if (users == null) {
            RequestDispatcher dispatcher = context.getRequestDispatcher("/login");
            dispatcher.forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private List<User> findUsersByAuthAndCookie(Cookie[] cookies) {
        List<User> users = null;
        for (Cookie cookie: cookies) {
            String nameCookie = cookie.getName();
            String authenticationNameCookie = "Auth";
            if (nameCookie.equals(authenticationNameCookie)) {
                users = usersService.getUserByAuth(cookie.getValue());
                break;
            }
        }
        return users;
    }

    @Override
    public void destroy() {

    }
}
