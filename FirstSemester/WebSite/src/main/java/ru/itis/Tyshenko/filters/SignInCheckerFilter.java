package ru.itis.Tyshenko.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "BrowserFilter", urlPatterns = "/service/*")
public class SignInCheckerFilter implements Filter {

    FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) {
        config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        if (session.getAttribute("authorized") == null || !session.getAttribute("authorized").equals("true")) {
            ((HttpServletResponse) servletResponse).sendRedirect(config.getServletContext().getContextPath() + "/signIn");
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        config = null;
    }
}
