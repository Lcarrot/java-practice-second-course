package ru.itis.Tyshenko;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.Tyshenko.entity.User;
import ru.itis.Tyshenko.jdbc.EntityManager;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        DataSource dataSource = getDataSource(properties);
        EntityManager manager = new EntityManager(dataSource);
        String tableName = "users";
        //manager.createTable(tableName, User.class);
        //manager.save(tableName ,new User(1L,"a","b",true));
        System.out.println(manager.findById(tableName, User.class, Long.class, 1L));
    }

    private static DataSource getDataSource(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setDriverClassName(properties.getProperty("db.driver.classname"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        return new HikariDataSource(config);
    }
}
