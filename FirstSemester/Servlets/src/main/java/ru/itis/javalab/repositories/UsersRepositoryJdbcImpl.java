package ru.itis.javalab.repositories;

import ru.itis.javalab.entity.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private DataSource dataSource;
    SimpleJdbcTemplate template;

    private RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .build();



    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new SimpleJdbcTemplate(dataSource);
    }

    //language=SQL
    private static final String SQL_FIND_ALL_BY_AGE = "select * from student where age = ?";

    @Override
    public List<User> findAllByAge(Long age) {
        return template.query(SQL_FIND_ALL_BY_AGE, userRowMapper, age);
    }

    @Override
    public void save(User entity) {

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
