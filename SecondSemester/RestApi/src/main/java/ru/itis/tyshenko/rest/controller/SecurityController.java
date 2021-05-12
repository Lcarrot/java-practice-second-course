package ru.itis.tyshenko.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.tyshenko.rest.dto.AccessTokenDto;
import ru.itis.tyshenko.rest.dto.RefreshTokenDto;
import ru.itis.tyshenko.rest.dto.SecurityDto;
import ru.itis.tyshenko.rest.form.LoginForm;
import ru.itis.tyshenko.rest.service.TokenService;

@RestController
public class SecurityController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<SecurityDto> login(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(tokenService.login(loginForm).get());
    }
    
    @PostMapping("/update/updateRefreshToken")
    public ResponseEntity<RefreshTokenDto> updateRefreshToken(@RequestHeader("REFRESH-TOKEN") String refreshToken,
                                                              @RequestHeader("REFRESH-TOKEN") String accessToken) {
        return ResponseEntity.ok(tokenService.updateRefreshToken(refreshToken).get());
    }

    @PostMapping("/update/updateAccessToken")
    public ResponseEntity<AccessTokenDto> updateAccessToken(@RequestHeader("REFRESH-TOKEN") String refreshToken,
                                                            @RequestHeader("REFRESH-TOKEN") String accessToken) {
        return ResponseEntity.ok(tokenService.updateAccessToken(refreshToken).get());
    }
}
