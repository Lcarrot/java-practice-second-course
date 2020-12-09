package ru.itis.Tyshenko.repositories.posts;

import ru.itis.Tyshenko.entity.Resume;
import ru.itis.Tyshenko.repositories.ReflectionCrudRepository;
import ru.itis.Tyshenko.repositories.utility.RowMapper;
import ru.itis.Tyshenko.repositories.utility.SqlJdbcTemplate;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ReflectionResumeRepository extends ReflectionCrudRepository<Resume> implements ResumeRepository {
    private final String[] fieldNames = {"header", "description", "contact", "user_id"};
    private final SqlJdbcTemplate<Resume> template;

    public ReflectionResumeRepository(DataSource source) {
        this.template = new SqlJdbcTemplate<>(source);
    }

    private final RowMapper<Resume> rowMapper =
            mapRow -> Resume.builder().id(mapRow.getLong("id")).
                    header(mapRow.getString("header")).
                    description(mapRow.getString("description"))
                    .contact(mapRow.getString("contact")).build();

    //language=SQL
    private String SQL_INSERT = "insert into resumes(header, description, contact, user_id) values (?,?,?,0)";

    @Override
    public void save(Resume entity) {
        List<Object> args;
        try {
            args = getParameters(entity, fieldNames);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        template.queryForChange(SQL_INSERT, args);
    }

    //language=SQL
    private String SQL_UPDATE = "update resumes set header = ?, description = ?" +
            "contact = ?, price = ? user_id = ? where id = ?";

    @Override
    public void update(Resume entity) {
        List<Object> args;
        try {
            args = getParameters(entity, fieldNames, entity.getId());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        template.queryForChange(SQL_UPDATE, args);
    }

    @Override
    public void delete(Resume entity) {
        List<Object> params = new LinkedList<>();
        params.add(entity.getId());
        //language=SQL
        String SQL_DELETE = "delete from resumes where id = ?";
        template.queryForChange(SQL_DELETE, params);
    }

    @Override
    public List<Resume> findAll() {
        //language=SQL
        String SQL_SELECT_ALL = "select * from resumes;";
        return template.queryForReceive(SQL_SELECT_ALL, rowMapper);
    }

    //language=SQL
    private String SQL_SELECT_BY_USER_ID = "select * from resumes where user_id = ?";
    @Override
    public Optional<Resume> findByUserId(Long userId) {
        return Optional.ofNullable(template.queryForObject(SQL_SELECT_BY_USER_ID, rowMapper, userId));
    }
}
