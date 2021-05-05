package ru.itis.tyshenko.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tyshenko.rest.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
