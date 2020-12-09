package ru.itis.Tyshenko.servlets.users;

import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.services.UserService;
import ru.itis.Tyshenko.statuses.SignInStatus;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "SignIn", value = "/signIn")
public class UserSignInServlet extends HttpServlet {

    private UserService userService;
    private final Map<SignInStatus, String> statusDescription = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
        fillMapDescriptions();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<UserDTO> optionalUser = userService.getByLogin(login);
        HttpSession session = req.getSession();
        if (optionalUser.isPresent() && userService.equalsRowPasswordWithUserPassword(password, optionalUser.get().getPassword())) {
            UserDTO user = optionalUser.get();
            session.setAttribute("authorized", "true");
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/service/profile");
        } else {
            SignInStatus status = optionalUser.isPresent() ? SignInStatus.PASSWORDS_DO_NOT_MATCH : SignInStatus.LOGIN_DOES_NOT_EXIST;
            session.setAttribute("authorized", "false");
            req.setAttribute("errorCode", statusDescription.get(status));
            req.getRequestDispatcher("/views/signIn.jsp").forward(req, resp);
        }
    }

    private void fillMapDescriptions() {
        statusDescription.put(SignInStatus.LOGIN_DOES_NOT_EXIST, "User with this login doesn't exist");
        statusDescription.put(SignInStatus.PASSWORDS_DO_NOT_MATCH, "Wrong password");
    }
}
