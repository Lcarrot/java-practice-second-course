package ru.itis.Tyshenko.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDTO {

    public Long id;
    public String login;
    public String email;
    public String country;
    public String password;
    public String gender;
}
