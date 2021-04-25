package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String login;
    private String email;
    private Integer age;

    public User to() {
        return User.builder().age(age).email(email).login(login).build();
    }
}
