package ru.itis.Tyshenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.itis.Tyshenko.dto.UserDto;
import ru.itis.Tyshenko.form.UserForm;
import ru.itis.Tyshenko.util.mail.generator.MailsGenerator;
import ru.itis.Tyshenko.util.mail.sender.MailSender;

import java.util.Optional;

@Service
@Profile("test")
public class TestUserService implements UserService{

    @Autowired
    private MailSender mailSender;

    @Autowired
    private MailsGenerator generator;

    @Value(value = "${spring.mail.username}")
    private String userName;

    @Value(value = "${server.url}")
    private String serverUrl;

    @Override
    public Optional<UserDto> getByLogin(String login) {
        return Optional.of(UserDto.builder().login(login)
                .country("Russia").gender("helicopter").build());
    }

    @Override
    public void add(UserForm entity) {
        mailSender.sendEmail("hi@mail.com", userName, "Hi",
                generator.generateConfirmEmail(serverUrl, "kaka"));
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        return Optional.of(UserDto.builder().login("leo")
                .country("Russia").gender("helicopter").build());
    }

    @Override
    public void update(UserForm before, UserForm now) {
        System.out.println("user was saved");
    }
}
