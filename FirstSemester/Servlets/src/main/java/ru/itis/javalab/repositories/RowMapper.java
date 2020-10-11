package ru.itis.javalab.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    T mapRow(ResultSet mapRow) throws SQLException;
}
