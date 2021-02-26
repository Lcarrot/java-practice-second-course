package ru.itis.Tyshenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.entity.User;
import ru.itis.Tyshenko.repository.users.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.Tyshenko.util.mail.sender.MailSender;
import ru.itis.Tyshenko.util.mail.generator.MailsGenerator;

import java.util.Optional;
import java.util.UUID;

@Service
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
    public Optional<UserDTO> getByLogin(String login) {
        Optional<User> optionalUser = userRepository.getByLogin(login);
        UserDTO userDTO = null;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userDTO = UserDTO.builder().id(user.getId()).
                    country(user.getCountry()).email(user.getEmail())
                    .gender(user.getGender() ? "male" : "female").login(user.getLogin()).password(user.getHashPassword()).build();
        }
        return Optional.ofNullable(userDTO);
    }

    @Override
    public boolean equalsRowPasswordWithUserPassword(String password, String user_hashPassword) {
        return passwordEncoder.matches(password, user_hashPassword);
    }

    @Override
    public void update(UserDTO entity, String password) {
        String hashPassword = passwordEncoder.encode(password);
        User user = User.builder().id(entity.getId()).login(entity.getLogin()).
                gender(entity.getGender().equals("male")).country(entity.getCountry())
                .email(entity.getEmail()).hashPassword(hashPassword).build();
        userRepository.update(user);
        entity.setPassword(hashPassword);
    }

    @Override
    public void add(UserDTO entity, String password) {
        String hashPassword = passwordEncoder.encode(password);
        User user = User.builder().id(null).login(entity.getLogin()).
                gender(entity.getGender().equals("male")).country(entity.getCountry())
                .email(entity.getEmail()).hashPassword(hashPassword).confirmCode(UUID.randomUUID().toString()).build();
        userRepository.save(user);
        entity.setPassword(hashPassword);
        entity.setId(user.getId());

        String confirmMail = mailsGenerator.generateConfirmEmail(serverUrl, user.getConfirmCode());
        mailSender.sendEmail(user.getEmail(), userName, "Authorization", confirmMail);
    }

    @Override
    public Optional<UserDTO> getById(Long id) {
        Optional<User> optionalUser = userRepository.getById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return Optional.of(UserDTO.builder().id(user.getId()).
                    country(user.getCountry()).email(user.getEmail())
                    .gender(user.getGender() ? "male" : "female").login(user.getLogin())
                    .password(user.getHashPassword()).build());
        }
        return Optional.empty();
    }
}
