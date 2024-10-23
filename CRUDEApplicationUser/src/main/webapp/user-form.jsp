<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Form</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="css/userform.css">
</head>
<body>
    <div class="header">
        <h1>User Management</h1>
    </div>
    <div class="form-container">
        <div class="form-card">
            <h2>${user == null ? 'Add New User' : 'Edit User'}</h2>
            <form action="user?action=${user == null ? 'insert' : 'update'}" method="post">
                <input type="hidden" name="id" value="${user.id}">
                Name: <input type="text" name="name" value="${user.name}"><br>
                Email: <input type="email" name="email" value="${user.email}"><br>
                Phone: <input type="text" name="phone" value="${user.phone}"><br>
                <input type="submit" value="Save">
            </form>
            <a href="user?action=list">Back</a>
        </div>
    </div>
    <div class="footer">
        <p>&copy; 2024 User Management System</p>
    </div>
</body>
</html> 
