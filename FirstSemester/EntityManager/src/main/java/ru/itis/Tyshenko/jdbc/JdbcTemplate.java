package ru.itis.Tyshenko.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcTemplate {

    private final DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> Optional<T> queryForObject(String sql, RowMapper<T> rowMapper, Object ... args) throws SQLException {
        try (PreparedStatement statement = getPreparedStatement(dataSource.getConnection(), sql, args);
             ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return Optional.of(rowMapper.mapRow(resultSet));
                }
        }
        return Optional.empty();
    }

    public void execute(String sql, Object ... args) throws SQLException {
        try (PreparedStatement statement = getPreparedStatement(dataSource.getConnection(), sql, args)){
            statement.execute();
        }
    }

    private PreparedStatement getPreparedStatement(Connection connection, String sql, Object[] args) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            statement.setObject(i+1, args[i]);
        }
        return statement;
    }
}
