package ru.itis.tyshenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tyshenko.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> getAdminByLoginAndPassword(String login, String password);
}
