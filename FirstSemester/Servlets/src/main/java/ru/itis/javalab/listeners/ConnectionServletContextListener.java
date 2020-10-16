package ru.itis.javalab.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.repositories.UsersRepositoryJdbcImpl;
import ru.itis.javalab.service.UsersService;
import ru.itis.javalab.service.UsersServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ConnectionServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();

        Properties properties = new Properties();
        try {
            properties.load(new FileReader("C:\\Users\\olga1\\Desktop\\projects\\javaLab\\FirstSemester\\Servlets\\src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariDataSource dataSource = new HikariDataSource(getHikariConfigFromProperties(properties));

        UsersRepository usersRepository =  new UsersRepositoryJdbcImpl(dataSource);
        UsersService usersService = new UsersServiceImpl(usersRepository);

        context.setAttribute("userService", usersService);
    }

    private HikariConfig getHikariConfigFromProperties(Properties properties) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driver.classname"));
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        return hikariConfig;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
