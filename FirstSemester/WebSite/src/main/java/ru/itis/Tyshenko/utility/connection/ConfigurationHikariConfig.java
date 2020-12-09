package ru.itis.Tyshenko.utility.connection;

import com.zaxxer.hikari.HikariConfig;

import java.util.Properties;

public class ConfigurationHikariConfig {

    private final Properties properties;

    ConfigurationHikariConfig(Properties properties) {
        this.properties = properties;
    }

    HikariConfig configureSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setDriverClassName(properties.getProperty("db.driver.classname"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        return config;
    }
}