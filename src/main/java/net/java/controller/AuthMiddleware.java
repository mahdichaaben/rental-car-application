package net.java.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.java.dao.CarDAO;
import net.java.dao.UserDAO;
import net.java.model.Car;
import net.java.model.User;

public class AuthMiddleware {
	


    private final UserDAO userDAO;

    public AuthMiddleware() {
        userDAO = new UserDAO();
    }

    public void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
        dispatcher.forward(request, response);
    }

    public void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signin.jsp");
        dispatcher.forward(request, response);
    }

    public void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");

        User newUser = new User(id, name, email, password, address, phoneNumber);
        try {
            userDAO.insertUser(newUser);
            response.sendRedirect("signin");
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
        }
    }
    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidate session to logout user
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Redirect to home page after logout
        response.sendRedirect("home");
    }
    public void checkuser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User authenticatedUser = userDAO.getUserByEmailAndPassword(email, password);
            HttpSession session = request.getSession();
            session.setAttribute("user", authenticatedUser);
            response.sendRedirect("home");
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("signin.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    
    
    public void accountsetting(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser != null) {
            String userId = authenticatedUser.getId();
            // Retrieve the user information from the database using the ID
            authenticatedUser = userDAO.getUserById(userId);
            session.setAttribute("user", authenticatedUser);
           
            RequestDispatcher dispatcher = request.getRequestDispatcher("accountsetting.jsp");
            dispatcher.forward(request, response);
        } else {
            // Handle case where user is not authenticated
            response.sendRedirect("signin.jsp");
        }
    }

    public boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean ans=session != null && session.getAttribute("user") != null;

        return ans ;
    }

    public boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            HttpSession session1 = request.getSession();
            User authenticatedUser = (User) session1.getAttribute("user");
            if( authenticatedUser.isAdmin()) {
            	
            }
            
        }
        return false;
    }
    
    
    
    
    
    
    public void updateUserInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");

        User updatedUser = new User(id, name, email, password, address, phoneNumber);
        try {
            userDAO.updateUser(updatedUser);
            // Update the user in the session after updating in the database
            HttpSession session = request.getSession();
            session.setAttribute("user", updatedUser);
            response.sendRedirect("account-settings");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
            User existingUser = userDAO.selectUser(id);
            request.setAttribute("user", existingUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("accountsetting.jsp");
            dispatcher.forward(request, response);
        }
    }
}
