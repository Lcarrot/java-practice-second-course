package ru.itis.Tyshenko.utility.connection;

import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

public class HikariDataSourceTuner {

    public static HikariDataSource getDataSource(Properties properties) {
        ConfigurationHikariConfig config = new ConfigurationHikariConfig(properties);
        return new HikariDataSource(config.configureSource());
    }
}

