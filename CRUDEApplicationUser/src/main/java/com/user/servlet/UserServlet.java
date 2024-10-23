package com.user.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.pojo.User;
import com.user.service.UserService;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    public void init() {
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // List all users
    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String pageParam = request.getParameter("page");
    	int pageNumber = 1;
        int pageSize = 10; 
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                pageNumber = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
               
                pageNumber = 1; 
            }
        }
        List<User> listUser = userService.getAllUsers(pageNumber, pageSize); 
        int totalUsersCount = userService.getTotalUsersCount(); 
        int totalPages = (int) Math.ceil((double) totalUsersCount / pageSize); 

        request.setAttribute("listUser", listUser);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    // Show form to add a new user
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    // Show form to edit an existing user
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userService.getUserById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

    // Insert a new user into the database
    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        User newUser = new User(name, email, phone);
        userService.addUser(newUser);
        response.sendRedirect("user?action=list");
    }

    // Update an existing user in the database
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("user id :::: " + id);
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        User updatedUser = new User(id, name, email, phone);
        userService.updateUser(updatedUser);
        response.sendRedirect("user?action=list");
    }

    // Delete a user from the database
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("delete id =====>>>" + id);
        userService.deleteUser(id);
        response.sendRedirect("user?action=list");
    }
}
