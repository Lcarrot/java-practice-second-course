package ru.itis.Tyshenko.servlets.ads;

import ru.itis.Tyshenko.dto.AdDTO;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.AdService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/service/myJobs")
public class GetUserAdsServlet extends HttpServlet {

    private AdService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        List<AdDTO> dtoList = service.getAllByUserID(userDTO.getId());
        req.setAttribute("ads", dtoList);
        req.getRequestDispatcher("/views/getAllAd.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
       service = (AdService) config.getServletContext().getAttribute("adService");
    }
}
