# TASK-2 - CRUD-Application-with-JSP-and-Servlets

URL Structure
1.Homepage (Start):

URL: /
Action: Displays the homepage or welcome page.

2.List Users:

URL: /user?action=list
Action: Fetches and displays the list of users (in userList.jsp).

3.Add User:

GET:
URL: /user?action=add
Action: Displays the form for adding a new user (in userForm.jsp).
POST:
URL: /user?action=insert
Action: Submits the new user data and processes the addition of a user.

4.Edit User:

GET:
URL: /user?action=edit&id={userId}
Action: Displays the form for editing an existing user (in userForm.jsp). Here, {userId} should be replaced with the ID of the user you want to edit.
POST:
URL: /user?action=update
Action: Submits the updated user data for processing.

5.Delete User:

URL: /user?action=delete&id={userId}
Action: Deletes a user based on the provided ID.
