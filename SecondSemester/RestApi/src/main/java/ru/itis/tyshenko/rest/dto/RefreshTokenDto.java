package ru.itis.tyshenko.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.tyshenko.rest.model.RefreshToken;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {

    private String refreshToken;
    private Date expiredTime;

    public static RefreshTokenDto from(RefreshToken refreshToken) {
        return RefreshTokenDto.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .expiredTime(refreshToken.getExpiredTime())
                .build();
    }
}
