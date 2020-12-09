package ru.itis.Tyshenko.services;
import ru.itis.Tyshenko.dto.UserDTO;

import java.util.Optional;


public interface UserService {

    Optional<UserDTO> getByLogin(String login);

    void add(UserDTO entity, String password);
    Optional<UserDTO> getById(Long id);
    boolean equalsRowPasswordWithUserPassword(String userHashedPassword, String password);
    void update(UserDTO entity, String password);
}
