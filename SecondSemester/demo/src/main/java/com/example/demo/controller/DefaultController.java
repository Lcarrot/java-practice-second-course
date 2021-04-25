package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import com.example.demo.util.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/user")
public class DefaultController {

    @Autowired
    private UserService userService;

    @Autowired
    private GenerateResponse generateResponse;

    @GetMapping("/{user-id}")
    public ResponseEntity<?> getUser(@PathVariable("user-id") Long id)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return generateResponse.generateByOptionalPresent(userService,"getById", id);
    }

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> saveUser(UserDto user)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return generateResponse.generateByOptionalPresent(userService, "save", user);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> saveUserByJson(@RequestBody UserDto user)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return generateResponse.generateByOptionalPresent(userService, "save", user);
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<?> deleteUser(@PathVariable("user-id") Long id)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return generateResponse.generateByOptionalPresent(userService, "delete", id);
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity<?> updateUser(@PathVariable("user-id") Long id, @RequestBody UserDto user)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return generateResponse.generateByOptionalPresent(userService,"update", id, user);
    }
}
