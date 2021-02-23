package ru.itis.Tyshenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.UserService;

import javax.servlet.http.HttpServletRequest;

public class SignInController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public String SignInPage() {
        return "signIn";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public String getUser(HttpServletRequest request, UserDTO userDTO) {
        userService.getByLogin(userDTO.login).ifPresent(user -> {
            if (userService.equalsRowPasswordWithUserPassword(user.password, userDTO.password)) {
                request.getSession().setAttribute("user", user);
            }
        });
        return "profile";
    }
}
