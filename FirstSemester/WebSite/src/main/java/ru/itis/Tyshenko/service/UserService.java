package ru.itis.Tyshenko.service;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.form.UserForm;

import java.util.Optional;


public interface UserService {

    Optional<UserForm> getByLogin(String login);

    void add(UserForm entity, String password);
    Optional<UserForm> getById(Long id);
    boolean equalsRowPasswordWithUserPassword(String userHashedPassword, String password);
    void update(UserForm entity, String password);
}
