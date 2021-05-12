package ru.itis.tyshenko.rest.security.token;

import lombok.extern.java.Log;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

@Component
@Log
public class AccessTokenAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("ACCESS-TOKEN");
        if (token != null) {
            AccessTokenAuthentication accessTokenAuthentication = new AccessTokenAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(accessTokenAuthentication);
            log.log(Level.INFO, "set auth in context");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
