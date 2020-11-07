package ru.itis.javalab.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (request.getSession().getAttribute("user") == null) {
            filterChain.doFilter(request, servletResponse);
        }
        else {
            ((HttpServletResponse) servletResponse).sendRedirect(context.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {

    }
}
