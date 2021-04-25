package ru.itis.tyshenko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tyshenko.entity.User;
import ru.itis.tyshenko.exception.ConvertToOtherObjectException;
import ru.itis.tyshenko.util.ReflectionConverter;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    public String login;
    public String country;
    public String gender;
}
