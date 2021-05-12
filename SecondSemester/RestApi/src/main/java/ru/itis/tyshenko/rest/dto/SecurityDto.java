package ru.itis.tyshenko.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityDto {

    private String refreshToken;
    private String accessToken;
    private Date refreshTokenExpiredTime;
    private Date accessTokenExpiredTime;
}
