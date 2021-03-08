package ru.itis.tyshenko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.tyshenko.dto.UserDto;
import ru.itis.tyshenko.form.UserForm;
import ru.itis.tyshenko.service.UserService;
import ru.itis.tyshenko.util.BindingResultMessages;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class SignUpController {

    private final UserService userService;
    private final BindingResultMessages bindingResultMessages;

    public SignUpController(UserService userService, BindingResultMessages bindingResultMessages) {
        this.userService = userService;
        this.bindingResultMessages = bindingResultMessages;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String SignUpPage(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "sign_up_page";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String saveNewUser(HttpServletRequest request, @Valid UserForm user, BindingResult result, Model model) {
        Optional<String> error = bindingResultMessages.getMessageFromError(result, "userForm.UnrepeatableFields");
        if (error.isPresent()) {
            model.addAttribute("repeatableFields", error.get());
            model.addAttribute("userForm", user);
            return "sign_up_page";
        }
        UserDto userDto = userService.add(user).get();

        request.getSession().setAttribute("user", userDto);
        return "redirect:/profile";
    }
}
