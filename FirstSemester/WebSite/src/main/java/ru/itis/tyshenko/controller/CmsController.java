package ru.itis.tyshenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.tyshenko.dto.CmsPageDto;
import ru.itis.tyshenko.form.CmsPageForm;
import ru.itis.tyshenko.service.CmsService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CmsController {

    @Autowired
    private CmsService service;

    @GetMapping("/admin/cms")
    public String getPage(CmsPageForm form, Model model) {
        model.addAttribute("pageForm", form);
        return "cms";
    }

    @PostMapping("/admin/cms")
    public String savePage(@Valid CmsPageForm form, BindingResult result) {
        if (!result.hasErrors()) {
            service.save(form);
            return "redirect:/admin/cmsList";
        }
        return "cms";
    }

    @GetMapping("/admin/cmsList")
    public String getList(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            try {
                Optional<CmsPageDto> cmsPageDto = service.getById(id);
                if (cmsPageDto.isPresent()) {
                    model.addAttribute("page", cmsPageDto.get());
                    return "cms_page";
                }
            } catch (NumberFormatException ignored) {
            }
        }
        model.addAttribute("pages", service.getAll());
        return "cms_list";
    }
}
