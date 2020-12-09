package ru.itis.Tyshenko.servlets.resume;

import ru.itis.Tyshenko.dto.ResumeDTO;
import ru.itis.Tyshenko.services.ResumeService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/allResume")
public class GetAllResumeServlet extends HttpServlet {

    private ResumeService resumeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ResumeDTO> resumes = resumeService.getAll();
        req.setAttribute("resumes", resumes);
        req.getRequestDispatcher("/views/getAllResume.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        resumeService = (ResumeService) config.getServletContext().getAttribute("resumeService");
    }
}
