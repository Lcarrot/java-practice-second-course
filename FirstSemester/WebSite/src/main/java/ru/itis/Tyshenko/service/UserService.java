package ru.itis.Tyshenko.service;
import ru.itis.Tyshenko.dto.UserDto;
import ru.itis.Tyshenko.form.UserForm;

import java.util.Optional;


public interface UserService {

    Optional<UserDto> getByLogin(String login);

    void add(UserForm entity);
    Optional<UserDto> getById(Long id);
    void update(UserForm before, UserForm now);
}
