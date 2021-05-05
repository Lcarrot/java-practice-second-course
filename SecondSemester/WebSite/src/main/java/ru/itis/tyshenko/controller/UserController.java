package ru.itis.tyshenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.tyshenko.dto.UserDto;
import ru.itis.tyshenko.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Controller
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String getUsersPage(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "profile_page";
    }

    @GetMapping(value = "/confirm/{code}")
    public String confirmRegistration(Principal principal, @PathVariable("code") String code) {
        Optional<UserDto> userDto = userService.confirmRegistration(code, principal);
        if (userDto.isPresent()) {
            return "redirect:/profile";
        }
        return "redirect:/signUp";
    }
}
