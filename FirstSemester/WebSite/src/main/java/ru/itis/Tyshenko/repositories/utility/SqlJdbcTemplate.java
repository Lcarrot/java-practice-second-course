package ru.itis.Tyshenko.repositories.utility;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SqlJdbcTemplate<T> {
    DataSource dataSource;

    public SqlJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public T queryForObject(String sql, RowMapper<T> rowMapper, Object ... arguments) {
        try (PreparedStatement statement = addParametersInStatement(sql, arguments);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                 return rowMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }

    public List<T> queryForReceive(String sql, RowMapper<T> rowMapper, Object ... arguments) {
        List<T> result = new LinkedList<>();
        try (PreparedStatement statement = addParametersInStatement(sql, arguments);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    public Optional<Object> queryForChange(String sql, List<Object> arguments) {
        Object[] args = arguments.toArray();
        try (PreparedStatement statement = addParametersInStatement(sql, args)) {
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return Optional.ofNullable(generatedKeys.getObject(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
    }

    private PreparedStatement addParametersInStatement(String sql, Object[] args) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
        }
        return statement;
    }
}
