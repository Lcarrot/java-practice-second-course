package ru.itis.tyshenko.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tyshenko.rest.model.RefreshToken;

import java.util.Optional;


public interface JwtRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String jwt);

    Optional<RefreshToken> findByUserId(Long id);
}
