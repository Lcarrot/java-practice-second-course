package ru.itis.Tyshenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.Tyshenko.dto.UserDto;
import ru.itis.Tyshenko.entity.User;
import ru.itis.Tyshenko.form.UserForm;
import ru.itis.Tyshenko.repository.UserRepository;
import ru.itis.Tyshenko.util.mail.generator.MailsGenerator;
import ru.itis.Tyshenko.util.mail.sender.MailSender;

import java.util.Optional;

@Service
@Profile("master")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private MailsGenerator mailsGenerator;

    @Autowired
    private MailSender mailSender;

    @Value(value = "${spring.mail.username}")
    private String userName;

    @Value(value = "${server.url}")
    private String serverUrl;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserDto> getByLogin(String login) {
        Optional<User> optionalUser = userRepository.getByLogin(login);
        return optionalUser.map(UserDto::convertFromUser);
    }

    @Override
    public void update(UserForm before,UserForm now) {
        User userBefore = before.convertToUser();
        userBefore.setHashPassword(passwordEncoder.encode(before.password));
        userRepository.delete(userBefore);
        User nowUser = now.convertToUser();
        nowUser.setHashPassword(passwordEncoder.encode(now.password));
        userRepository.delete(userBefore);
        userRepository.save(nowUser);
    }

    @Override
    public void add(UserForm entity) {
        User user = entity.convertToUser();
        user.setHashPassword(entity.password);
        userRepository.save(user);
        entity.setId(user.getId());

        String confirmMail = mailsGenerator.generateConfirmEmail(serverUrl, user.getConfirmCode());
        mailSender.sendEmail(user.getEmail(), userName, "Authorization", confirmMail);
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        Optional<User> optionalUser = userRepository.getById(id);
        return optionalUser.map(UserDto::convertFromUser);
    }
}
