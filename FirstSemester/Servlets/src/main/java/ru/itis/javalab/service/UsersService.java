package ru.itis.javalab.service;

import ru.itis.javalab.entity.User;
import java.util.List;

public interface UsersService {
    List<User> getAllUsers();
    List<User> getUserByAuth(String auth);
    void insertUser(User user);
}
