package ru.itis.Tyshenko.entity;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class User {

    private Long id;
    private String login;
    private String email;
    private String country;
    private String hashPassword;
    private Boolean gender;
}
