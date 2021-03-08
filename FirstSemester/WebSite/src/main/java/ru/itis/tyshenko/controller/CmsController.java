package ru.itis.tyshenko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CmsController {

    @GetMapping("/admin/cms")
    public String getPage() {
        return "cms";
    }

    @PostMapping("/admin/cms")
    public String savePage() {
        return "";
    }

    // TODO: 03/03/2021 Сделать cms(качественно)
}
