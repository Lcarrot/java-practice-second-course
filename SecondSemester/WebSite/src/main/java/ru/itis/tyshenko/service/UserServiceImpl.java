package ru.itis.tyshenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.tyshenko.annotation.ConvertField;
import ru.itis.tyshenko.dto.UserDto;
import ru.itis.tyshenko.entity.User;
import ru.itis.tyshenko.exception.ConvertToOtherObjectException;
import ru.itis.tyshenko.form.SignUpUserForm;
import ru.itis.tyshenko.repository.UserRepository;
import ru.itis.tyshenko.util.ReflectionConverter;
import ru.itis.tyshenko.mail.generator.MailsGenerator;
import ru.itis.tyshenko.mail.sender.MailSender;

import java.security.Principal;
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

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           MailsGenerator mailsGenerator, MailSender mailSender, ReflectionConverter converter) {
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
    public Optional<UserDto> getByEmail(String email) {
        return userRepository.getByEmail(email).map(this::convertFromEntityToDto);
    }

    @Override
    public Optional<UserDto> authorize(String email, String password) {
        Optional<User> user = userRepository.getByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getHashPassword())) {
            return user.map(this::convertFromEntityToDto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> update(SignUpUserForm before, SignUpUserForm now) {
        User userBefore = convertFromFormToEntity(before);
        userBefore.setHashPassword(passwordEncoder.encode(before.password));
        User nowUser = convertFromFormToEntity(now);
        nowUser.setHashPassword(passwordEncoder.encode(now.password));
        userRepository.delete(userBefore);
        userRepository.save(nowUser);
        return Optional.of(convertFromEntityToDto(nowUser));
    }

    @Override
    public Optional<UserDto> confirmRegistration(String code, Principal principal) {
        Optional<User> opUser = userRepository.findByConfirmCode(code);
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (user.getEmail().equals(principal.getName())) {
                userRepository.delete(user);
                user.setConfirmed(true);
                userRepository.save(user);
                return Optional.of(convertFromEntityToDto(user));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> add(SignUpUserForm entity) {
        User user = convertFromFormToEntity(entity);
        user.setConfirmCode(UUID.randomUUID().toString());
        user.setRole(User.Role.USER);
        user.setHashPassword(passwordEncoder.encode(entity.password));
        userRepository.save(user);
        mailSender.sendEmail(user.getEmail(), userName, "Authorization",
                mailsGenerator.generateConfirmEmail(serverUrl, user.getConfirmCode()));
        return Optional.of(convertFromEntityToDto(user));
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        return userRepository.getById(id).map(this::convertFromEntityToDto);
    }

    private User convertFromFormToEntity(SignUpUserForm form) {
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
