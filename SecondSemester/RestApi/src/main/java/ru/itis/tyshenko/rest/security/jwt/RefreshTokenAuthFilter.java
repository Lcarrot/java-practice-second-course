package ru.itis.tyshenko.rest.security.jwt;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.tyshenko.rest.dto.SecurityDto;
import ru.itis.tyshenko.rest.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;

@Component
@Log
public class RefreshTokenAuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AccessTokenAuthentication accessTokenAuthentication =
                (AccessTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (accessTokenAuthentication != null) {
            Optional<SecurityDto> token = tokenService.getRefreshToken(request.getHeader("REFRESH-TOKEN"));
            if (token.isPresent()) {
                if (token.get().getExpiredTime().compareTo(new Date()) < 0) {
                   throw new UsernameNotFoundException("refresh token is expired");
                }
                log.log(Level.INFO, "refresh token is valid");
            } else {
                throw new UsernameNotFoundException("refresh token isn't valid");
            }
        }
        filterChain.doFilter(request, response);
    }
}
