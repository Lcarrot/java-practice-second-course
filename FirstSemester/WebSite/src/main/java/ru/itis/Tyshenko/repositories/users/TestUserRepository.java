package ru.itis.Tyshenko.repositories.users;


import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.itis.Tyshenko.entity.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Log
@Repository
@Profile("test")
public class TestUserRepository implements UserRepository {
    @Override
    public void save(User entity) {
        System.out.println("student was saved");
        log.log(Level.INFO, "student was saved");
    }

    @Override
    public void update(User entity) {
        log.log(Level.INFO, "student was updated");
    }

    @Override
    public void delete(User entity) {
        log.log(Level.INFO,"student was deleted");
    }

    @Override
    public List<User> findAll() {
        return new LinkedList<>();
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }
}
