package ru.itis.tyshenko.rest.service;

import ru.itis.tyshenko.rest.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getById(Long id);
    Optional<User> save(User user);
    Optional<User> delete(Long id);
    Optional<User> update(Long id, User user);
}
