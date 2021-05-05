package ru.itis.tyshenko.rest.service;

import ru.itis.tyshenko.rest.dto.SecurityDto;
import ru.itis.tyshenko.rest.form.LoginForm;

import java.util.Optional;

public interface TokenService {

    Optional<SecurityDto> login(LoginForm loginForm);

    Optional<SecurityDto> refresh(String refreshToken);

    Optional<SecurityDto> getRefreshToken(String string);
}
