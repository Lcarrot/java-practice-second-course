package ru.itis.Tyshenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getUsersPage(HttpServletRequest request, Model model) {
        model.addAttribute("user", userService.getById(((UserDTO) request.getSession().getAttribute("user")).id).get());
        return "profile";
    }
}
