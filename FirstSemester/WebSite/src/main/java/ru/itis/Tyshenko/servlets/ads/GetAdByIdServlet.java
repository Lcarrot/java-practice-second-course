package ru.itis.Tyshenko.servlets.ads;

import ru.itis.Tyshenko.dto.AdDTO;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.AdService;
import ru.itis.Tyshenko.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/jobs/job")
public class GetAdByIdServlet extends HttpServlet {

    private AdService adService;
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        Optional<AdDTO> adDTOOptional = adService.getById(id);
        if (adDTOOptional.isPresent()) {
            AdDTO adDTO = adDTOOptional.get();
            Optional<UserDTO> userDTO = userService.getById(adDTO.user_id);
            userDTO.ifPresent(dto -> adDTO.user_login = dto.login);
            req.setAttribute("ad", adDTO);
        }
        req.getRequestDispatcher("/views/job.jsp").forward(req, resp);
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        adService = (AdService) context.getAttribute("adService");
        userService = (UserService) context.getAttribute("userService");
    }
}
