package ru.itis.tyshenko.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.tyshenko.rest.model.User;
import ru.itis.tyshenko.rest.service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class DefaultController {

    @Autowired
    private UserService userService;

    @GetMapping("/{user-id}")
    public ResponseEntity<User> getUser(@PathVariable("user-id") Long id) throws
            InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return generateAnswer("getById", id);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestParam User user) {
        return userService.save(user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<User> deleteUser(@PathVariable("user-id") Long id) {
        return userService.delete(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity<User> updateUser(@PathVariable("user-id") Long id, @RequestParam User user) {
        return userService.update(id, user).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    private ResponseEntity<User> generateAnswer(String methodName, Object ... attributes) throws
            NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?>[] classes = (Class<?>[]) Arrays.stream(attributes).map(Object::getClass).toArray();
        Method method = userService.getClass().getMethod(methodName, classes);
        return ((Optional<User>) method.invoke(userService, attributes))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
