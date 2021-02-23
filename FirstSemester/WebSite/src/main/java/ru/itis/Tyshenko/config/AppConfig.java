package ru.itis.Tyshenko.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan("ru.itis.Tyshenko")
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty("db.hikari.max-pool-size"))));
        hikariConfig.setUsername(environment.getProperty("db.username"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setDriverClassName(environment.getProperty("db.driver.classname"));
        return hikariConfig;
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftlh");
        resolver.setContentType("text/html;charset=UTF-8");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/ftl/");
        return configurer;
    }

    @Bean
    public freemarker.template.Configuration configuration() {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(
                new SpringTemplateLoader(new ClassRelativeResourceLoader(this.getClass()),
                        "/"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return configuration;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(environment.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("spring.mail.port"))));

        mailSender.setUsername(environment.getProperty("spring.mail.username"));
        mailSender.setPassword(environment.getProperty("spring.mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        props.put("mail.smtp.allow8bitmime", environment.getProperty("spring.mail.properties.mail.smtp.allow8bitmime"));
        props.put("mail.smtp.ssl.trust", environment.getProperty("spring.mail.properties.mail.smtp.ssl.trust"));
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", environment.getProperty("spring.mail.properties.mail.debug"));

        return mailSender;
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

}
