package ru.itis.javalab.entity;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String password;
    private String auth;
}
