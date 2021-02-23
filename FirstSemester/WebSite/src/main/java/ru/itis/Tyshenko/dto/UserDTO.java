package ru.itis.Tyshenko.dto;

import lombok.*;
import ru.itis.Tyshenko.validation.Password;
import ru.itis.Tyshenko.validation.UnrepeatableFields;

import javax.validation.constraints.Email;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@UnrepeatableFields(fields = {"country", "gender"}, message = "{sign_up_page.wrong.password}")
public class UserDTO {

    public Long id;
    public String login;
    @Email(message = "{sign_up_page.wrong.email}")
    public String email;
    public String country;
    @Password(message = "{sign_up_page.wrong.equal_fields}")
    public String password;
    public String gender;
}
