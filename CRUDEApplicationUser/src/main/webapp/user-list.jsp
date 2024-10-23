<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Management</title>
<link rel="stylesheet" type="text/css" href="css/userlist.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

</head>
<body>
	<header>
		<h1>User Management System</h1>
	</header>

	<h2 class="center-text">List of Users</h2>
	<a href="user?action=new" class="button">Add New User</a>
	<table border="1">
		<thead>
			<tr>
				<th><center>Id</center></th>
				<th><center>Name</center></th>
				<th><center>Email</center></th>
				<th><center>Phone</center></th>
				<th><center>Actions</center></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${listUser}">
				<tr>
					<td><center>${user.id}</center></td>
					<td><center>${user.name}</center></td>
					<td><center>${user.email}</center></td>
					<td><center>${user.phone}</center></td>
					<td>
						<center>
							<a href="user?action=edit&id=${user.id}"
								onclick="return confirm('Are you sure you want to update?');">
								<i class="fas fa-pencil-alt"></i>
							</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
								href="user?action=delete&id=${user.id}"
								onclick="return confirm('Are you sure you want to delete?');"><i
								class="fas fa-trash-alt"></i></a>
						</center>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">
		<c:if test="${currentPage > 1}">
			<a href="user?action=list&page=${currentPage - 1}">Previous</a>
		</c:if>

		<c:forEach begin="1" end="${totalPages}" var="i">
			<c:if test="${i == currentPage}">
				<strong>${i}</strong>
			</c:if>
			<c:if test="${i != currentPage}">
				<a href="user?action=list&page=${i}">${i}</a>
			</c:if>
		</c:forEach>

		<c:if test="${currentPage < totalPages}">
			<a href="user?action=list&page=${currentPage + 1}">Next</a>
		</c:if>
	</div>


</body>
</html>
