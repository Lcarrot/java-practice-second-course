package ru.itis.Tyshenko.repositories.users;

import ru.itis.Tyshenko.entity.User;
import ru.itis.Tyshenko.repositories.ReflectionCrudRepository;
import ru.itis.Tyshenko.repositories.utility.RowMapper;
import ru.itis.Tyshenko.repositories.utility.SqlJdbcTemplate;

import javax.sql.DataSource;
import java.util.*;

public class ReflectionUserRepository extends ReflectionCrudRepository<User> implements UserRepository {
    private final String[] fieldNames = {"login", "email", "gender", "country", "hashPassword"};
    private final SqlJdbcTemplate<User> template;

    public ReflectionUserRepository(DataSource source) {
        this.template = new SqlJdbcTemplate<>(source);
    }

    private final RowMapper<User> rowMapper =
            mapRow -> User.builder()
                    .id(mapRow.getLong("id")).login(mapRow.getString("login").trim()).
                    email(mapRow.getString("email").trim()).gender(mapRow.getBoolean("gender")).
                    country(mapRow.getString("country").trim()).hashPassword(mapRow.getString("hashPassword").trim()).
                    build();

    //language=SQL
    private final String SQL_INSERT = "insert into users(login, email, gender, country, hashPassword) values (?,?,?,?,?)";

    @Override
    public void save(User entity) {
        List<Object> args;
        try {
            args = getParameters(entity, fieldNames);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        Optional<Object> objectOptional = template.queryForChange(SQL_INSERT, args);
        objectOptional.ifPresent(o -> entity.setId((Long) o));
    }

    //language=SQL
    private final String SQL_UPDATE = "update users set login = ?," +
            " email = ?, gender = ?, country = ?, hashPassword = ? where id = ?";

    @Override
    public void update(User entity) {
        List<Object> args;
        try {
            args = getParameters(entity, fieldNames, entity.getId());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        template.queryForChange(SQL_UPDATE, args);
    }

    //language=SQL
    private final String SQL_DELETE = "delete from users where id = ?";

    @Override
    public void delete(User entity) {
        List<Object> params = new LinkedList<>();
        params.add(entity.getId());
        template.queryForChange(SQL_DELETE, params);
    }

    //language=SQL
    private final String SQL_SELECT_ALL = "select * from users;";

    @Override
    public List<User> findAll() {
        return template.queryForReceive(SQL_SELECT_ALL, rowMapper);
    }

    //language=SQL
    private final String SQL_SELECT_BY_LOGIN = "select * from users where login = ?";

    @Override
    public Optional<User> getByLogin(String login) {
        User user = template.queryForObject(SQL_SELECT_BY_LOGIN, rowMapper, login);
        return Optional.ofNullable(user);
    }

    //language=SQL
    private final String SQL_SELECT_BY_ID = "SELECT * from users where id = ?";

    @Override
    public Optional<User> getById(Long id) {
        User user = template.queryForObject(SQL_SELECT_BY_ID, rowMapper, id);
        return Optional.ofNullable(user);
    }
}
