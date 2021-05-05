package ru.itis.Tyshenko;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.Tyshenko.entity.User;
import ru.itis.Tyshenko.jdbc.EntityManager;
import ru.itis.Tyshenko.jdbc.database.PostgresqlDatabase;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        EntityManager manager = new EntityManager(properties);
        String tableName = "users";
//        manager.createTable(tableName, User.class);
//        manager.save(tableName ,new User(2L,"d","c",true));
        System.out.println(manager.findById(tableName, User.class, Long.class, 2L));
    }
}
