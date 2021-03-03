package ru.itis.Tyshenko.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.Tyshenko.entity.User;
import ru.itis.Tyshenko.validation.Password;
import ru.itis.Tyshenko.validation.UnrepeatableFields;

import javax.validation.constraints.Email;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@UnrepeatableFields(message = "{sign_up_page.wrong.equal_fields}", fields = {"country", "gender"})
public class UserForm {

    public Long id;
    public String login;
    @Email(message = "{sign_up_page.wrong.email}")
    public String email;
    public String country;
    @Password(message = "{sign_up_page.wrong.password}")
    public String password;
    public String gender;

    public User convertToUser() {
        return User.builder().id(id).login(login).
                gender(gender).country(country)
                .email(email).build();
    }
}
