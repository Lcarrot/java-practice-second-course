package ru.itis.Tyshenko.servlets.users;

import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.UserService;
import ru.itis.Tyshenko.statuses.SignUpStatus;
import ru.itis.Tyshenko.utility.messages.PreparerMessage;
import ru.itis.Tyshenko.utility.messages.PreparerMessageForUserSignUp;
import ru.itis.Tyshenko.utility.PreparedRequestTemplateForEntity;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "SignUp", value = "/signUp")
public class UserSignUpServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/signUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = PreparedRequestTemplateForEntity.getUserDtoFromRequest(req);
        String password = req.getParameter("password");
        String confirm_password = req.getParameter("confirm_password");
        boolean agreed = req.getParameter("agree") != null;
        boolean rememberMe = req.getParameter("remember-checkbox") != null;
        PreparerMessage<SignUpStatus> preparer = new PreparerMessageForUserSignUp(userDTO, password, confirm_password);
        HttpSession session= req.getSession();
        if (preparer.checkFields() && agreed) {
            session.setAttribute("authorized", "true");
            userService.add(userDTO, password);
            session.setAttribute("user", userDTO);
            resp.sendRedirect(req.getContextPath() + "/service/profile");
        }
        else {
            session.setAttribute("authorized", "false");
            String agreedMessage = "if you don't agree with our rules, you can't use our site =()";
            String error = preparer.getMessage(agreedMessage);
            req.setAttribute("description", error);
            req.getRequestDispatcher("/views/signUp.jsp").forward(req, resp);
        }
    }
}
