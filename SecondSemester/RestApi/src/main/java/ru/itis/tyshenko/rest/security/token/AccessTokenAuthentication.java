package ru.itis.tyshenko.rest.security.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.tyshenko.rest.security.details.UserDetailsImpl;

import java.util.Collection;

public class AccessTokenAuthentication implements Authentication {

    private UserDetailsImpl userDetails;

    private boolean isAuthenticated;

    private final String accessToken;

    private boolean refreshTokenIsValid;

    public AccessTokenAuthentication(String jwt) {
        this.accessToken = jwt;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = (UserDetailsImpl) userDetails;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        if (userDetails != null) {
            return userDetails.getUser();
        }
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public boolean isRefreshTokenIsValid() {
        return refreshTokenIsValid;
    }

    public void setRefreshTokenIsValid(boolean refreshTokenIsValid) {
        this.refreshTokenIsValid = refreshTokenIsValid;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return accessToken;
    }
}
