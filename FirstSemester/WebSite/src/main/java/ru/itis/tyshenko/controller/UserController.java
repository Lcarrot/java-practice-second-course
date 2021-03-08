package ru.itis.tyshenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.tyshenko.dto.UserDto;
import ru.itis.tyshenko.entity.User;
import ru.itis.tyshenko.form.UserForm;
import ru.itis.tyshenko.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getUsersPage(HttpServletRequest request, Model model) {
        model.addAttribute("user", userService.getByLogin((
                (UserDto) request.getSession().getAttribute("user")).getLogin()).get());
        return "profile_page";
    }

    @GetMapping(value = "/confirm")
    public String confirmRegistration(HttpServletRequest request, @Param("code") String code) {
        Optional<UserDto> userDto = userService.confirmRegistration(code);
        if (userDto.isPresent()) {
            request.getSession().setAttribute("user", userDto.get());
            return "redirect:/profile";
        }
        return "redirect:/signUp";
    }
}
