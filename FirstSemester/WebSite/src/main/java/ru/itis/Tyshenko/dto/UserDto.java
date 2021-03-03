package ru.itis.Tyshenko.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.Tyshenko.entity.User;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    public String login;
    public String country;
    public String gender;

    public static UserDto convertFromUser(User user) {
        return UserDto.builder().country(user.getCountry())
                .gender(user.getGender()).login(user.getLogin())
                .build();
    }
}
