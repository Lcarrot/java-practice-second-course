package ru.itis.javalab.repositories;

import ru.itis.javalab.entity.User;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    SimpleJdbcTemplate<User> template;

    private RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .build();

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        template = new SimpleJdbcTemplate<>(dataSource);
    }

    //language=SQL
    private static final String SQL_FIND_ALL_BY_AGE = "select * from student where age = ?";

    @Override
    public List<User> findAllByAge(Long age) {
        return template.query(SQL_FIND_ALL_BY_AGE, userRowMapper, age);
    }

    //language=SQL
    private static final String SQL_FIND_ALL_BY_AUTH = "select * from student where auth = ?";
    @Override
    public List<User> findUserByAuth(String auth) {
        return template.query(SQL_FIND_ALL_BY_AUTH, userRowMapper, auth);
    }

    //language=SQL
    private static final String SQL_SAVE_USER = "insert into student(first_name, last_name, age, auth) " +
            "values (?,?,?,?)";

    @Override
    public void save(User entity) {
        template.queryWithoutResult(SQL_SAVE_USER, userRowMapper, entity.getFirstName(),
                entity.getLastName(), entity.getAge());
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    //language=SQL
    private static final String SQL_FIND_ALL = "select * from student";

    @Override
    public List<User> findAll() {
        return template.query(SQL_FIND_ALL, userRowMapper);
    }
}
