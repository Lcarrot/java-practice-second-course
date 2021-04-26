package ru.itis.tyshenko.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tyshenko.annotation.ConvertField;
import ru.itis.tyshenko.dto.UserDto;
import ru.itis.tyshenko.entity.User;
import ru.itis.tyshenko.exception.ConvertToOtherObjectException;
import ru.itis.tyshenko.form.UserForm;
import ru.itis.tyshenko.repository.UserRepository;
import ru.itis.tyshenko.util.ReflectionConverter;
import ru.itis.tyshenko.util.mail.generator.MailsGenerator;
import ru.itis.tyshenko.util.mail.sender.MailSender;

import java.util.Optional;
import java.util.UUID;

@Service
@Profile("master")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailsGenerator mailsGenerator;
    private final MailSender mailSender;
    private final ReflectionConverter converter;

    @Value(value = "${spring.mail.username}")
    private String userName;

    @Value(value = "${server.url}")
    private String serverUrl;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailsGenerator mailsGenerator, MailSender mailSender, ReflectionConverter converter)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailsGenerator = mailsGenerator;
        this.mailSender = mailSender;
        this.converter = converter;
    }

    @Override
    public Optional<UserDto> getByLogin(String login) {
        return userRepository.getByLogin(login).map(this::convertFromEntityToDto);
    }

    @Override
    public Optional<UserDto> update(UserForm before,UserForm now) {
        User userBefore = convertFromFormToEntity(before);
        userBefore.setHashPassword(passwordEncoder.encode(before.password));
        User nowUser = convertFromFormToEntity(now);
        nowUser.setHashPassword(passwordEncoder.encode(now.password));
        userRepository.delete(userBefore);
        userRepository.save(nowUser);
        return Optional.of(convertFromEntityToDto(nowUser));
    }

    @Override
    public Optional<UserDto> confirmRegistration(String code) {
        Optional<User> opUser = userRepository.findByConfirmCode(code);
        if (opUser.isPresent()) {
            User user = opUser.get();
            userRepository.delete(user);
            opUser.get().setConfirmed(true);
            userRepository.save(user);
            return Optional.of(convertFromEntityToDto(opUser.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> add(UserForm entity) {
        User user = convertFromFormToEntity(entity);
        user.setConfirmCode(UUID.randomUUID().toString());
        user.setHashPassword(passwordEncoder.encode(entity.password));
        user = userRepository.save(user);
        String confirmMail = mailsGenerator.generateConfirmEmail(serverUrl, user.getConfirmCode());
        mailSender.sendEmail(user.getEmail(), userName, "Authorization", confirmMail);
        UserDto dto = convertFromEntityToDto(user);
        System.out.println(dto);
        return Optional.of(dto);
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        return userRepository.getById(id).map(this::convertFromEntityToDto);
    }

    private User convertFromFormToEntity(UserForm form) {
        try {
            return converter.convertToOtherObjectWithSpecialAnnotation(form, User.class, ConvertField.class);
        } catch (ConvertToOtherObjectException e) {
            throw new IllegalStateException(e);
        }
    }

    private UserDto convertFromEntityToDto(User user) {
        try {
            return converter.convertToOtherObject(user, UserDto.class);
        } catch (ConvertToOtherObjectException e) {
            throw new IllegalStateException(e);
        }
    }
}
