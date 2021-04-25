package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> save(UserDto user) {
        return Optional.of(userRepository.save(user.to()));
    }

    @Override
    public Optional<User> delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> userRepository.delete(value));
        return user;
    }

    @Override
    public Optional<User> update(Long id, UserDto user) {
        Optional<User> dbUser = userRepository.findById(id);
        dbUser.ifPresent((user1 -> {
            user1.setAge(user.getAge());
            user1.setEmail(user.getEmail());
            user1.setLogin(user.getLogin());
            userRepository.save(user1);
        }));
        return dbUser;
    }


}
