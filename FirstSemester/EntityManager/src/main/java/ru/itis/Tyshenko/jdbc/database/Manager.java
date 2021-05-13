package ru.itis.Tyshenko.jdbc.database;

import java.util.List;
import java.util.Optional;

public interface Manager {

    <T> void createTable(String table, Class<T> entityClass);

    <T> List<T> findAll(String table, Class<T> entityClass);

    <T> List<T> find(String tableName, Class<T> tClass, String sql);

    void save(String table, Object object);

    <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue);
}
