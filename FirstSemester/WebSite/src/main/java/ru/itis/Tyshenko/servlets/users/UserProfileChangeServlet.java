package ru.itis.Tyshenko.servlets.users;
import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.UserService;
import ru.itis.Tyshenko.statuses.ChangeUserStatus;
import ru.itis.Tyshenko.utility.messages.PreparerMessage;
import ru.itis.Tyshenko.utility.messages.PreparerMessageForUserChange;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.itis.Tyshenko.utility.DataChecker.*;

@WebServlet(name = "ChangeUserData", value = "/service/profile/change")
public class UserProfileChangeServlet extends HttpServlet {

    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        req.setAttribute("user", user);
        req.getRequestDispatcher("/views/changeProfile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        if (userService.equalsRowPasswordWithUserPassword(password, user.getPassword())) {
            PreparerMessage<ChangeUserStatus> preparer = new PreparerMessageForUserChange(email);
            if (preparer.checkFields()) {
                changeProfile(user, email, country);
                req.getSession().setAttribute("user", user);
                userService.update(user, password);
                resp.sendRedirect(req.getContextPath() + "/service/profile");
            }
            else {
                String error = preparer.getMessage();
                req.setAttribute("description", error);
                req.getRequestDispatcher("/views/changeProfile.jsp").forward(req, resp);
            }
        }
        else {
            req.setAttribute("description", "This password doesn't match with yours");
            req.getRequestDispatcher("/views/changeProfile.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }

    private void changeProfile(UserDTO userDTO, String email, String country) {
        userDTO.setEmail(email);
        if (checkCountry(country)) userDTO.setCountry(country);
    }
}
