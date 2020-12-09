package ru.itis.Tyshenko.repositories.users;

import ru.itis.Tyshenko.entity.User;
import ru.itis.Tyshenko.repositories.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {

    Optional<User> getByLogin(String login);
    Optional<User> getById(Long id);
}
