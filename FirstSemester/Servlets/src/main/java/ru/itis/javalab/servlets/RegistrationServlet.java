package ru.itis.javalab.servlets;

import ru.itis.javalab.entity.User;
import ru.itis.javalab.service.UsersService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "RegistrationServlet", value = "/login")
public class RegistrationServlet extends HttpServlet {

    ServletContext context;
    UsersService usersService;
    @Override
    public void init(ServletConfig config) {
        this.context = config.getServletContext();
        this.usersService = (UsersService) context.getAttribute("userService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String last_name = request.getParameter("last_name");
        Integer age = Integer.valueOf(request.getParameter("age"));
        String password = request.getParameter("password");
        String auth = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("auth", auth);
        cookie.setMaxAge(60*60*24*30);
        User user = User.builder().firstName(name).lastName(last_name).age(age).password(password).auth(auth).build();
        usersService.insertUser(user);
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/servlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        context.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }
}
