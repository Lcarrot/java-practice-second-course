package ru.itis.Tyshenko.servlets.ads;

import ru.itis.Tyshenko.dto.AdDTO;
import ru.itis.Tyshenko.services.AdService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/jobs")
public class GetAdsServlet extends HttpServlet {

    private AdService adService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AdDTO> ads = adService.getAll();
        req.setAttribute("ads", ads);
        req.getRequestDispatcher("/views/getAllAd.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        adService = (AdService) config.getServletContext().getAttribute("adService");
    }
}
