package ru.itis.Tyshenko.servlets.resume;

import ru.itis.Tyshenko.dto.AdDTO;
import ru.itis.Tyshenko.dto.ResumeDTO;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.AdService;
import ru.itis.Tyshenko.services.ResumeService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(value = "/service/myResume")
public class UserResumeServlet extends HttpServlet {

    private ResumeService resumeService;
    private AdService adService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        Optional<ResumeDTO> optionalResume = resumeService.getByUserId(userDTO.id);
        if (optionalResume.isPresent()) {
            ResumeDTO resume = optionalResume.get();
            Optional<List<AdDTO>> optionalAdDTOS = adService.getAllByResumeId(resume.id);
            if (optionalAdDTOS.isPresent()) {
                req.setAttribute("adsIsEmpty", "false");
                req.setAttribute("ads", optionalAdDTOS.get());
            }
            else {
                req.setAttribute("adsIsEmpty", "true");
            }
            req.setAttribute("resume", resume);
            req.getRequestDispatcher("/views/getAllResume.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getServletContext() + "/service/myResume/create");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        resumeService = (ResumeService) config.getServletContext().getAttribute("resumeService");
        adService = (AdService) config.getServletContext().getAttribute("adService");
    }
}
