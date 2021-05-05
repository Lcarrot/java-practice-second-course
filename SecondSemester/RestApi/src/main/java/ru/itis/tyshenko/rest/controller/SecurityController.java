package ru.itis.tyshenko.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    
    @PostMapping("/refreshAccessToken")
    public ResponseEntity<SecurityDto> refreshToken(String refreshToken) {
        return ResponseEntity.ok(tokenService.refresh(refreshToken).get());
    }
}
