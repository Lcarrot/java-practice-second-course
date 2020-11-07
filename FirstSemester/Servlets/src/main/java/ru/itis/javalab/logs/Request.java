package ru.itis.javalab.logs;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class Request {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(Request.class);

    public static void log(HttpServletRequest request) {
        logger.info(request.getServletPath());
    }
}
