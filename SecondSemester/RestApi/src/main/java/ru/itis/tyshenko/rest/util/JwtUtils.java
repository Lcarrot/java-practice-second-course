package ru.itis.tyshenko.rest.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.tyshenko.rest.dto.AccessTokenDto;
import ru.itis.tyshenko.rest.model.RefreshToken;
import ru.itis.tyshenko.rest.model.User;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {

    @Value("${refresh.token.time}")
    private Long refreshTokenAliveTime;

    @Value("${access.token.time}")
    private Long accessTokenAliveTime;

    @Value("${jwt.key}")
    private String signingKey;

    public RefreshToken createRefreshToken(User user) {
        return RefreshToken.builder().refreshToken(UUID.randomUUID().toString()).user(user)
                .expiredTime(new Date(System.currentTimeMillis() + refreshTokenAliveTime))
                .build();
    }

    public AccessTokenDto createAccessToken(User user) {
        Date expirationTime = new Date(System.currentTimeMillis() + accessTokenAliveTime);
         String token = new DefaultJwtBuilder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().toString())
                 .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, signingKey).compact();
         return AccessTokenDto.builder().jwt(token).expiredTime(expirationTime).build();
    }

    public Claims getClaims(String jwt) {
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwt).getBody();
    }
}
