package ru.itis.tyshenko.service;
import ru.itis.tyshenko.dto.UserDto;
import ru.itis.tyshenko.form.SignUpUserForm;

import java.security.Principal;
import java.util.Optional;


public interface UserService {

    Optional<UserDto> getByLogin(String login);
    Optional<UserDto> getByEmail(String email);
    Optional<UserDto> authorize(String email, String password);
    Optional<UserDto> add(SignUpUserForm entity);
    Optional<UserDto> getById(Long id);
    Optional<UserDto> update(SignUpUserForm before, SignUpUserForm now);
    Optional<UserDto> confirmRegistration(String code, Principal principal);
}
