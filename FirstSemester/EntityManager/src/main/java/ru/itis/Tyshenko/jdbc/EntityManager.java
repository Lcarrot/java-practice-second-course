package ru.itis.Tyshenko.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.Tyshenko.converter.IDatabase;
import ru.itis.Tyshenko.jdbc.criteria.CriteriaBuilder;
import ru.itis.Tyshenko.jdbc.criteria.SqlExpression;
import ru.itis.Tyshenko.jdbc.database.Database;
import ru.itis.Tyshenko.jdbc.database.PostgresqlDatabase;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

public class EntityManager {

    private final Database database;

    public EntityManager(Properties properties) {
        String driver = (String) properties.get("db.driver.classname");
        DataSource dataSource = getDataSource(properties);
        database = findDatabase(driver, dataSource);
    }

    public <T> void createTable(String tableName, Class<T> entityClass) {
        database.createTable(tableName, entityClass);
    }

    public void save(String tableName, Object entity) {
        database.save(tableName, entity);
    }

    public <T, ID> Optional<T> findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
        return database.findById(tableName, resultType, idType, idValue);
    }

    public <T> Optional<T> find(String tableName, SqlExpression expression, String puk) {
        return Optional.empty();
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

    public IDatabase getDatabase() {
        return database.getType();
    }

    public <T> CriteriaBuilder<T> getCriteriaBuilderForObject(T object, String tableName) {
        return new CriteriaBuilder<>(this, object, tableName);
    }
}
