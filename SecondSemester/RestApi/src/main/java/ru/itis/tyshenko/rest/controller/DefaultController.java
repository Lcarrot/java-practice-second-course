package ru.itis.tyshenko.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.itis.tyshenko.rest.dto.SecurityDto;
import ru.itis.tyshenko.rest.dto.UserDto;
import ru.itis.tyshenko.rest.model.User;
import ru.itis.tyshenko.rest.service.UserService;

@RestController
@RequestMapping("/user")
public class DefaultController {

    @Autowired
    private UserService userService;

    @GetMapping("/{user-id}")
    public ResponseEntity<UserDto> getUser(@RequestHeader("ACCESS-TOKEN") String access,
                                           @RequestHeader("REFRESH-TOKEN") String refresh,
                                           @PathVariable("user-id") Long id) {
        return ResponseEntity.ok(userService.getById(id).orElseThrow(() -> new UsernameNotFoundException("don't found")));
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestHeader("JWT-TOKEN") String token, @RequestParam User user) {
        return ResponseEntity.ok(userService.save(user).orElseThrow(() -> new UsernameNotFoundException("don't found")));
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<UserDto> deleteUser(@RequestHeader("JWT-TOKEN") String token, @PathVariable("user-id") Long id) {
        return ResponseEntity.ok(userService.delete(id).orElseThrow(() -> new UsernameNotFoundException("don't found")));
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity<UserDto> updateUser(@RequestHeader("JWT-TOKEN") String token, @PathVariable("user-id") Long id, @RequestParam User user) {
        return ResponseEntity.ok(userService.update(id, user).orElseThrow(() -> new UsernameNotFoundException("don't found")));
    }

}
