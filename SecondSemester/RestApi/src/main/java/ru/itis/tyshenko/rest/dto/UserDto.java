package ru.itis.tyshenko.rest.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.tyshenko.rest.model.Role;
import ru.itis.tyshenko.rest.model.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
public class UserDto {

    private String login;
    private String email;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Role role;

    public static UserDto from(User user) {
        return UserDto.builder().login(user.getLogin())
                .email(user.getEmail())
                .age(user.getAge())
                .role(user.getRole()).build();
    }
}
