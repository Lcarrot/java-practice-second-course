package ru.itis.tyshenko.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tyshenko.rest.model.User;
import ru.itis.tyshenko.rest.repository.UserRepository;

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
    public Optional<User> save(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(value -> userRepository.delete(value));
        return user;
    }

    @Override
    public Optional<User> update(Long id, User user) {
        Optional<User> dbUser = userRepository.findById(id);
        dbUser.ifPresent((user1 -> {
            user1.setId(id);
            userRepository.save(user);
        }));
        return dbUser;
    }
}
