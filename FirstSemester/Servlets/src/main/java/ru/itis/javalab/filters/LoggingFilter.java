package ru.itis.javalab.filters;

import ru.itis.javalab.logs.Request;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LoggingFilter", value = "*")
public class LoggingFilter implements Filter {

    public void init(FilterConfig config) {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        Request.log(request);
        chain.doFilter(request, resp);
    }

    public void destroy() {
    }
}
