package ru.itis.Tyshenko.servlets.ads;


import ru.itis.Tyshenko.dto.AdDTO;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.AdService;
import ru.itis.Tyshenko.statuses.AdStatus;
import ru.itis.Tyshenko.utility.messages.PreparerMessage;
import ru.itis.Tyshenko.utility.messages.PreparerMessageForAd;
import ru.itis.Tyshenko.utility.PreparedRequestTemplateForEntity;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreateJobServlet", value = "/service/myJobs/create")
public class AddAdServlet extends HttpServlet {

    private AdService adService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/createJob.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        AdDTO ad = PreparedRequestTemplateForEntity.getAdDtoFromRequest(req);
        PreparerMessage<AdStatus> preparer = new PreparerMessageForAd(ad);
        if (preparer.checkFields()) {
            adService.add(ad, user.getId());
            resp.sendRedirect(req.getContextPath() + "/service/myJob");
        }
        else {
            String error = preparer.getMessage();
            req.setAttribute("error", error);
            req.getRequestDispatcher("/views/createJob.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        adService = (AdService) config.getServletContext().getAttribute("adService");
    }
}
