package ru.itis.Tyshenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.Tyshenko.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByLogin(String login);

    Optional<User> getById(Long id);
}
