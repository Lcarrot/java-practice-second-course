package ru.itis.Tyshenko.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.Tyshenko.jdbc.criteria.CriteriaBuilder;
import ru.itis.Tyshenko.jdbc.criteria.Query;
import ru.itis.Tyshenko.jdbc.criteria.Root;
import ru.itis.Tyshenko.jdbc.database.Database;
import ru.itis.Tyshenko.jdbc.database.Manager;
import ru.itis.Tyshenko.jdbc.database.PostgresqlDatabase;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class EntityManager implements Manager {

    private final Database database;

    public EntityManager(Properties properties) {
        String driver = (String) properties.get("db.driver.classname");
        DataSource dataSource = getDataSource(properties);
        database = findDatabase(driver, dataSource);
    }

    public <T> void createTable(String tableName, Class<T> entityClass) {
        database.createTable(tableName, entityClass);
    }

    @Override
    public <T> List<T> findAll(String table, Class<T> entityClass) {
        return database.findAll(table, entityClass);
    }

    @Override
    public <T> List<T> find(String tableName, Class<T> tClass, String sql) {
        return database.find(tableName, tClass, sql);
    }

    public void save(String tableName, Object entity) {
        database.save(tableName, entity);
    }

    public <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        return database.findById(tableName, resultType, idType, idValue);
    }

    private DataSource getDataSource(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setDriverClassName(properties.getProperty("db.driver.classname"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        return new HikariDataSource(config);
    }

    private Database findDatabase(String driver, DataSource dataSource) {
        if (driver.contains("postgres")) {
            return new PostgresqlDatabase(dataSource);
        }
        throw new IllegalArgumentException();
    }

    public String createSqlStringFromCriteria(Query query, String tableName) {
       return database.createSqlStringFromCriteria(query, tableName);
    }

    public <T> CriteriaBuilder getCriteriaBuilderForSelect(Class<T> object) {
        return CriteriaBuilder.select(new Root(object));
    }
}
