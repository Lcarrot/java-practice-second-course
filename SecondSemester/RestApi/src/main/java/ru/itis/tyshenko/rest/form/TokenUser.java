package ru.itis.tyshenko.rest.form;

import lombok.Builder;
import lombok.Data;
import ru.itis.tyshenko.rest.model.Role;

@Data
@Builder
public class TokenUser {

    private String email;
    private Role role;
    private long createTokenTime;
}
