package ru.itis.javalab.service;

import ru.itis.javalab.entity.User;
import ru.itis.javalab.repositories.UsersRepository;

import java.util.List;

public class UsersServiceImpl implements UsersService {

    private final UsersRepository repository;

    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public List<User> getUserByAuth(String auth) {
        return repository.findUserByAuth(auth);
    }

    @Override
    public void insertUser(User user) {
        repository.save(user);
    }
}
