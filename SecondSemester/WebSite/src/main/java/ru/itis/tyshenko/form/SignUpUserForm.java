package ru.itis.tyshenko.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tyshenko.annotation.ConvertField;
import ru.itis.tyshenko.validation.Password;
import ru.itis.tyshenko.validation.UnrepeatableFields;

import javax.validation.constraints.Email;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@UnrepeatableFields(message = "{sign_up_page.wrong.equal_fields}", fields = {"country", "gender"})
public class SignUpUserForm {

    @ConvertField
    public String login;
    @ConvertField @Email(message = "{sign_up_page.wrong.email}")
    public String email;
    @ConvertField
    public String country;
    @Password(message = "{sign_up_page.wrong.password}")
    public String password;
    @ConvertField
    public String gender;

}
