package ru.itis.Tyshenko.jdbc;

import java.sql.ResultSet;

public interface RowMapper<T> {

    T rowMapper(ResultSet resultSet);
}
