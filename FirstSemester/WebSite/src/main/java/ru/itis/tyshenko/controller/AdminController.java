package ru.itis.tyshenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.tyshenko.dto.AdminDto;
import ru.itis.tyshenko.form.AdminForm;
import ru.itis.tyshenko.service.AdminService;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/signIn")
    public String getSignIn() {
        return "admin_signIn_page";
    }

    @PostMapping("admin/signIn")
    public String checkSignIn(AdminForm adminForm) {
        Optional<AdminDto> adminDto = adminService.authenticate(adminForm);
        if (adminDto.isPresent()) {
            return "admin_page_lists";
        }
        return "redirect:/admin/lists";
    }

    @GetMapping("admin/lists")
    public String getPageLists() {
        return "admin_page_lists";
    }
}
