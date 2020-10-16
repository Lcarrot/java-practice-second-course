package ru.itis.javalab.repositories;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleJdbcTemplate<T> {

    DataSource dataSource;

    public SimpleJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = doAlways(sql, rowMapper, args);
            resultSet = statement.executeQuery();
            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    // ignore
                }
            }
        }
    }

    private PreparedStatement doAlways(String sql, RowMapper<T> rowMapper, Object... args) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = dataSource.getConnection();
        statement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
        }
        return statement;

    }

    public void queryWithoutResult(String sql, RowMapper<T> rowMapper, Object... args) {
        PreparedStatement statement = null;

        try {
            statement = doAlways(sql, rowMapper, args);
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    // ignore
                }
            }
        }
    }
}
