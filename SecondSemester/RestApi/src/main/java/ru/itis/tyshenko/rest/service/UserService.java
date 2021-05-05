package ru.itis.tyshenko.rest.service;

import ru.itis.tyshenko.rest.dto.UserDto;
import ru.itis.tyshenko.rest.model.User;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> getById(Long id);
    Optional<UserDto> save(User user);
    Optional<UserDto> delete(Long id);
    Optional<UserDto> update(Long id, User user);
}
