package ru.itis.tyshenko.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
            RefreshToken refreshToken = jwtUtils.createRefreshToken(user.get());
            jwtRepository.save(refreshToken);
            return Optional.of(SecurityDto.builder()
                    .refreshToken(refreshToken.getRefreshToken())
                    .expiredTime(refreshToken.getExpiredTime())
                    .accessToken(jwtUtils.createAccessToken(user.get()).getJwt())
                    .build());
        }
        return Optional.empty();
    }

    @Override
    public Optional<SecurityDto> refresh(String token) {
        Optional<RefreshToken> refreshToken = jwtRepository.findByRefreshToken(token);
        if (refreshToken.isPresent()) {
            User user = refreshToken.get().getUser();
            jwtRepository.delete(refreshToken.get());
            RefreshToken newRefreshToken = jwtUtils.createRefreshToken(refreshToken.get().getUser());
            jwtRepository.save(newRefreshToken);
            return Optional.of(SecurityDto.builder()
                    .refreshToken(newRefreshToken.getRefreshToken())
                    .expiredTime(newRefreshToken.getExpiredTime())
                    .accessToken(jwtUtils.createAccessToken(user).getJwt())
                    .build());
        }
        return Optional.empty();
    }

    public Optional<SecurityDto> getRefreshToken(String token) {
        return jwtRepository.findByRefreshToken(token)
                .map(refreshToken -> SecurityDto.builder()
                        .refreshToken(refreshToken.getRefreshToken())
                        .expiredTime(refreshToken.getExpiredTime())
                        .build());
    }
}
