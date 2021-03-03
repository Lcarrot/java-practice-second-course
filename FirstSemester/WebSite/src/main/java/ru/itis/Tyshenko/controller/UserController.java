package ru.itis.Tyshenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.Tyshenko.form.UserForm;
import ru.itis.Tyshenko.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getUsersPage(HttpServletRequest request, Model model) {
        model.addAttribute("user", userService.getById(((UserForm) request.getSession().getAttribute("user")).id).get());
        return "profile_page";
    }
}