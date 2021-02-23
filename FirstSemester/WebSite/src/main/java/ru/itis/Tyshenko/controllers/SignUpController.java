package ru.itis.Tyshenko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Objects;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String SignUpPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "sign_up_page";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String saveNewUser(HttpServletRequest request, @Valid UserDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().stream().anyMatch(objectError -> {
                Arrays.stream(Objects.requireNonNull(objectError.getCodes()))
                        .filter(x -> x.equals("userDTO.UnrepeatableFields"))
                        .forEach(x-> model.addAttribute("repeatableFields", objectError.getDefaultMessage()));
                return true;
            });
            model.addAttribute("userDTO", user);
            return "sign_up_page";
        }
        userService.add(user, user.password);
        request.getSession().setAttribute("user", user);
        return "redirect:/profile";
    }
}
