package ru.itis.tyshenko.controller;

import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.tyshenko.form.SignUpUserForm;
import ru.itis.tyshenko.service.UserService;
import ru.itis.tyshenko.util.BindingResultMessages;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/signUp")
@PreAuthorize("permitAll()")
public class SignUpController {

    private final UserService userService;
    private final BindingResultMessages bindingResultMessages;

    public SignUpController(UserService userService, BindingResultMessages bindingResultMessages) {
        this.userService = userService;
        this.bindingResultMessages = bindingResultMessages;
    }

    @GetMapping
    public String SignUpPage(Model model) {
        model.addAttribute("userForm", new SignUpUserForm());
        return "sign_up_page";
    }

    @SneakyThrows
    @PostMapping
    public String saveNewUser(@Valid SignUpUserForm user, BindingResult result, Model model, HttpServletRequest request) {
        Optional<String> error = bindingResultMessages.getMessageFromError(result, "userForm.UnrepeatableFields");
        if (error.isPresent()) {
            model.addAttribute("repeatableFields", error.get());
            model.addAttribute("userForm", user);
            return "sign_up_page";
        }
        userService.add(user);
        request.login(user.getEmail(), user.getPassword());
        return "redirect:/profile";
    }
}
