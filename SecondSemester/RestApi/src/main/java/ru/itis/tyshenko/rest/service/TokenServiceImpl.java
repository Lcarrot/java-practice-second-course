package ru.itis.tyshenko.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tyshenko.rest.dto.AccessTokenDto;
import ru.itis.tyshenko.rest.dto.RefreshTokenDto;
import ru.itis.tyshenko.rest.dto.SecurityDto;
import ru.itis.tyshenko.rest.form.LoginForm;
import ru.itis.tyshenko.rest.model.RefreshToken;
import ru.itis.tyshenko.rest.model.User;
import ru.itis.tyshenko.rest.repository.JwtRepository;
import ru.itis.tyshenko.rest.repository.UserRepository;
import ru.itis.tyshenko.rest.util.JwtUtils;

import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Optional<SecurityDto> login(LoginForm loginForm) {
        Optional<User> user = userRepository.findByEmail(loginForm.getEmail());
        if (user.isPresent() && passwordEncoder.matches(loginForm.getPassword(), user.get().getHashPassword())) {
            jwtRepository.findByUserId(user.get().getId()).ifPresent(token -> jwtRepository.delete(token));
            return Optional.of(createSecurityDto(jwtRepository.save(jwtUtils.createRefreshToken(user.get())),
                    jwtUtils.createAccessToken(user.get())));
        }
        return Optional.empty();
    }

    @Override
    public Optional<RefreshTokenDto> updateRefreshToken(String token) {
        Optional<RefreshToken> refreshToken = jwtRepository.findByRefreshToken(token);
        if (refreshToken.isPresent()) {
            jwtRepository.delete(refreshToken.get());
            return Optional.of(RefreshTokenDto
                    .from(jwtRepository.save(jwtUtils.createRefreshToken(refreshToken.get().getUser()))));
        }
        return Optional.empty();
    }

    public Optional<RefreshTokenDto> getRefreshToken(String token) {
        return jwtRepository.findByRefreshToken(token).map(RefreshTokenDto::from);
    }

    @Override
    public Optional<AccessTokenDto> updateAccessToken(String refreshToken) {
        return jwtRepository.findByRefreshToken(refreshToken)
                .map(token -> jwtUtils.createAccessToken(token.getUser()));
    }

    private SecurityDto createSecurityDto(RefreshToken refreshToken, AccessTokenDto accessTokenDto) {
        return SecurityDto.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .refreshTokenExpiredTime(refreshToken.getExpiredTime())
                .accessToken(accessTokenDto.getJwt())
                .accessTokenExpiredTime(accessTokenDto.getExpiredTime())
                .build();
    }
}
