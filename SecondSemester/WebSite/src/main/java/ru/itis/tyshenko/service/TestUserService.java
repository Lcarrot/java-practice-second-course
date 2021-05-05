package ru.itis.tyshenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.itis.tyshenko.dto.UserDto;
import ru.itis.tyshenko.form.SignUpUserForm;
import ru.itis.tyshenko.mail.generator.MailsGenerator;

import java.security.Principal;
import java.util.Optional;

@Service
@Profile("test")
public class TestUserService implements UserService{

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
    public Optional<UserDto> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> authorize(String email, String password) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> add(SignUpUserForm entity) {
        System.out.println(generator.generateConfirmEmail(serverUrl, "kaka"));
        return Optional.of(UserDto.builder().login("xD")
                .country("Russia").gender("helicopter").build());
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        return Optional.of(UserDto.builder().login("leo")
                .country("Russia").gender("helicopter").build());
    }

    @Override
    public Optional<UserDto> update(SignUpUserForm before, SignUpUserForm now) {
        System.out.println("user was saved");
        return Optional.of(UserDto.builder().login("leo")
                .country("Russia").gender("helicopter").build());
    }

    @Override
    public Optional<UserDto> confirmRegistration(String code, Principal principal) {
        return Optional.empty();
    }
}
