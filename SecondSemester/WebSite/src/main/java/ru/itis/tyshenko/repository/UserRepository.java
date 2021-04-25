package ru.itis.tyshenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tyshenko.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByLogin(String login);

    Optional<User> getByEmail(String email);

    Optional<User> getById(Long id);

    Optional<User> findByConfirmCode(String code);
}
