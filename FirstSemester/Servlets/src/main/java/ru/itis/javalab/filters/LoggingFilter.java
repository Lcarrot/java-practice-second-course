package ru.itis.javalab.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LoggingFilter", value = "*")
public class LoggingFilter implements Filter {

    Logger logger;
    public void init(FilterConfig config) {
        logger = LoggerFactory.getLogger(LoggingFilter.class);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        logger.info(request.getServletPath());
        chain.doFilter(req, resp);
    }

    public void destroy() {
        logger = null;
    }
}
