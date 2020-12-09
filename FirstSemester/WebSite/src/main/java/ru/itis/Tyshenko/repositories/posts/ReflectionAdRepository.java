package ru.itis.Tyshenko.repositories.posts;

import ru.itis.Tyshenko.entity.Ad;
import ru.itis.Tyshenko.repositories.ReflectionCrudRepository;
import ru.itis.Tyshenko.repositories.utility.RowMapper;
import ru.itis.Tyshenko.repositories.utility.SqlJdbcTemplate;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ReflectionAdRepository extends ReflectionCrudRepository<Ad> implements AdRepository {
    private final String[] fieldNames = {"header", "description", "contact", "price" , "user_id", "resume_id"};
    private final SqlJdbcTemplate<Ad> template;

    public ReflectionAdRepository(DataSource source) {
        this.template = new SqlJdbcTemplate<>(source);
    }

    private final RowMapper<Ad> rowMapper =
            mapRow -> Ad.builder().id(mapRow.getLong("id")).
                    header(mapRow.getString("header")).
                    description(mapRow.getString("description"))
                    .contact(mapRow.getString("contact")).price(mapRow.getLong("price")).build();

    //language=SQL
    private String SQL_INSERT = "insert into ads(header, description, contact, price, user_id, resume_id)" +
            " values (?,?,?,?,?,?)";

    @Override
    public void save(Ad entity) {
        List<Object> args;
        try {
            args = getParameters(entity, fieldNames);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        template.queryForChange(SQL_INSERT, args);
    }

    //language=SQL
    private String SQL_UPDATE = "update ads set header = ?, description = ?, contact = ?, price = ?, resume_id = ? " +
            "where id = ?";

    @Override
    public void update(Ad entity) {
        List<Object> args;
        try {
            args = getParameters(entity, fieldNames, entity.getId());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        template.queryForChange(SQL_UPDATE, args);
    }

    //language=SQL
    private final String SQL_DELETE = "delete from ads where id = ?";

    @Override
    public void delete(Ad entity) {
        List<Object> params = new LinkedList<>();
        params.add(entity.getId());
        template.queryForChange(SQL_DELETE, params);
    }

    //language=SQL
    private final String SQL_SELECT_ALL = "select * from ads;";

    @Override
    public List<Ad> findAll() {
        return template.queryForReceive(SQL_SELECT_ALL, rowMapper);
    }

    //language=SQL
    String SQL_SELECT_ALL_BY_USER_ID = "select * from ads where user_id = ?;";

    @Override
    public List<Ad> getAllByUserID(Long userID) {
        return template.queryForReceive(SQL_SELECT_ALL_BY_USER_ID, rowMapper, userID);
    }

    //language=SQL
    String SQL_SELECT_BY_ID = "select * from ads where user_id = ?;";

    @Override
    public Optional<Ad> getById(Long id) {
        return Optional.ofNullable(template.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
    }

    //language=SQL
    private final String SQL_SELECT_BY_RESUME_ID = "select * from ads where resume_id = ?";

    @Override
    public Optional<List<Ad>> getByResumeID(Long resumeID) {
        return Optional.ofNullable(template.queryForReceive(SQL_SELECT_BY_RESUME_ID, rowMapper, resumeID));
    }
}
