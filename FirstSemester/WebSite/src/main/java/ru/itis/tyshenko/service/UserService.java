package ru.itis.tyshenko.service;
import ru.itis.tyshenko.dto.UserDto;
import ru.itis.tyshenko.form.UserForm;

import java.util.Optional;


public interface UserService {

    Optional<UserDto> getByLogin(String login);

    Optional<UserDto> add(UserForm entity);
    Optional<UserDto> getById(Long id);
    Optional<UserDto> update(UserForm before, UserForm now);
    Optional<UserDto> confirmRegistration(String code);
}
