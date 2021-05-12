package ru.itis.tyshenko.rest.security.token;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.tyshenko.rest.util.JwtUtils;

@Component
public class AccessTokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    @Qualifier("CustomUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccessTokenAuthentication accessTokenAuthentication = (AccessTokenAuthentication) authentication;
        Claims claims = jwtUtils.getClaims(authentication.getName());
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        accessTokenAuthentication.setAuthenticated(true);
        accessTokenAuthentication.setUserDetails(userDetails);
        return accessTokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AccessTokenAuthentication.class.equals(aClass);
    }
}
