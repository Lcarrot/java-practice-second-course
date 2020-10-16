package ru.itis.javalab.repositories;

import ru.itis.javalab.entity.User;
import java.util.List;

public interface UsersRepository extends CrudRepository<User> {
    List<User> findAllByAge(Long age);
    List<User> findUserByAuth(String uuid);
}
