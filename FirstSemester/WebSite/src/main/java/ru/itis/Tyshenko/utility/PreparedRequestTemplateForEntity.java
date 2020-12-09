package ru.itis.Tyshenko.utility;

import ru.itis.Tyshenko.dto.AdDTO;
import ru.itis.Tyshenko.dto.ResumeDTO;
import ru.itis.Tyshenko.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

public class PreparedRequestTemplateForEntity {

    public static UserDTO getUserDtoFromRequest(HttpServletRequest request) {
        return UserDTO.builder().login(request.getParameter("login")).email(request.getParameter("email")).country(request.getParameter("country")).
                gender(request.getParameter("gender")).build();
    }

    public static AdDTO getAdDtoFromRequest(HttpServletRequest request) {
        return AdDTO.builder().id(null).header(request.getParameter("header")).
                description(request.getParameter("description")).
                contact(request.getParameter("contact")).price(request.getParameter("price")).build();
    }

    public static ResumeDTO getResumeDtoFromRequest(HttpServletRequest request) {
        return ResumeDTO.builder().id(null).header(request.getParameter("header")).
                description(request.getParameter("description")).
                contact(request.getParameter("contact")).build();
    }
}
