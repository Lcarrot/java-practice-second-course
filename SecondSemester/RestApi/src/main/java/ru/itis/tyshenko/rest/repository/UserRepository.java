package ru.itis.tyshenko.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tyshenko.rest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
