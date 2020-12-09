package ru.itis.Tyshenko.listeners;

import ru.itis.Tyshenko.repositories.posts.AdRepository;
import ru.itis.Tyshenko.repositories.posts.ReflectionAdRepository;
import ru.itis.Tyshenko.repositories.posts.ReflectionResumeRepository;
import ru.itis.Tyshenko.repositories.posts.ResumeRepository;
import ru.itis.Tyshenko.repositories.users.ReflectionUserRepository;
import ru.itis.Tyshenko.repositories.users.UserRepository;
import itis.Tyshenko.services.*;
import ru.itis.Tyshenko.services.*;
import ru.itis.Tyshenko.utility.connection.HikariDataSourceTuner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ServiceListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("/resources/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        DataSource dataSource = HikariDataSourceTuner.getDataSource(properties);

        UserRepository userRepository = new ReflectionUserRepository(dataSource);
        UserService userService = new UserServiceImpl(userRepository, passwordEncoder);
        servletContext.setAttribute("userService", userService);

        AdRepository adRepository = new ReflectionAdRepository(dataSource);
        AdService adService = new AdServiceImpl(adRepository);
        servletContext.setAttribute("adService", adService);

        ResumeRepository resumeRepository = new ReflectionResumeRepository(dataSource);
        ResumeService resumeService = new ResumeServiceImpl(resumeRepository);
        servletContext.setAttribute("resumeService", resumeService);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
