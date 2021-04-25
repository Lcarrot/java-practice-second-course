package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getById(Long id);
    Optional<User> save(UserDto user);
    Optional<User> delete(Long id);
    Optional<User> update(Long id, UserDto user);
}
