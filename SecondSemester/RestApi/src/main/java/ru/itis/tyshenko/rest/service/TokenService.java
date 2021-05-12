package ru.itis.tyshenko.rest.service;

import ru.itis.tyshenko.rest.dto.AccessTokenDto;
import ru.itis.tyshenko.rest.dto.RefreshTokenDto;
import ru.itis.tyshenko.rest.dto.SecurityDto;
import ru.itis.tyshenko.rest.form.LoginForm;

import java.util.Optional;

public interface TokenService {

    Optional<SecurityDto> login(LoginForm loginForm);

    Optional<RefreshTokenDto> getRefreshToken(String string);

    Optional<RefreshTokenDto> updateRefreshToken(String refreshToken);

    Optional<AccessTokenDto> updateAccessToken(String refreshToken);
}
