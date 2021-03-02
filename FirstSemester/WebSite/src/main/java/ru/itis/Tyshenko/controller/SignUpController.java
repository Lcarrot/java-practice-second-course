package ru.itis.Tyshenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.form.UserForm;
import ru.itis.Tyshenko.service.UserService;
import ru.itis.Tyshenko.util.BindingResultMessages;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String SignUpPage(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "sign_up_page";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String saveNewUser(HttpServletRequest request, @Valid UserForm user, BindingResult result, Model model) {
        Optional<String> error = BindingResultMessages.getMessageFromError(result, "userForm.UnrepeatableFields");
        if (error.isPresent()) {
            model.addAttribute("repeatableFields", error.get());
            model.addAttribute("userForm", user);
            return "sign_up_page";
        }
        userService.add(user, user.password);
        request.getSession().setAttribute("user", user);
        return "redirect:/profile_page";
    }
}
