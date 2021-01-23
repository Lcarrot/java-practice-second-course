package ru.itis.javalab.servlets;

import ru.itis.javalab.service.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletExample", value = "/service/profile")
public class ServletExample extends HttpServlet {

    private UsersService usersService;

    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        this.usersService = (UsersService) context.getAttribute("userService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        System.out.println(usersService.getAllUsers());
    }

}
