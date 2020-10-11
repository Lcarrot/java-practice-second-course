package ru.itis.javalab.service;

import ru.itis.javalab.entity.User;
import ru.itis.javalab.repositories.UsersRepository;

import java.util.List;

public class UsersServiceImpl implements UsersService {

    private UsersRepository repository;

    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
