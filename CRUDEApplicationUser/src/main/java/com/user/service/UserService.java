package com.user.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user.pojo.User;

public class UserService {
	 private String jdbcURL = "jdbc:mysql://localhost:3306/user_management";
	    private String jdbcUsername = "root";
	    private String jdbcPassword = "root"; // Update this to your own password

	    private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, phone) VALUES " +
	            " (?, ?, ?);";
	    private static final String SELECT_USER_BY_ID = "select id, name, email, phone from users where id =?";
	    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	    private static final String UPDATE_USERS_SQL = "update users set name = ?, email= ?, phone =? where id = ?;";
	    private static final String SELECT_ALL_USERS_PAGED = "SELECT * FROM users LIMIT ? OFFSET ?;";
	    private static final String COUNT_USERS_SQL = "SELECT COUNT(*) FROM users;";

	    protected Connection getConnection() {
	        Connection connection = null;
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return connection;
	    }

	    public void addUser(User user) {
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
	            preparedStatement.setString(1, user.getName());
	            preparedStatement.setString(2, user.getEmail());
	            preparedStatement.setString(3, user.getPhone());
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }

	    public User getUserById(int id) {
	        User user = null;
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
	            preparedStatement.setInt(1, id);
	            ResultSet rs = preparedStatement.executeQuery();

	            while (rs.next()) {
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String phone = rs.getString("phone");
	                user = new User(id, name, email, phone);
	                System.out.println("userr ====>>> " + user);
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return user;
	    }

	    public List<User> getAllUsers(int page, int size) {
	        List<User> users = new ArrayList<>();
	        int offset = (page - 1) * size; 
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS_PAGED)) {
	            preparedStatement.setInt(1, size); 
	            preparedStatement.setInt(2, offset); 
	            ResultSet rs = preparedStatement.executeQuery();

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String phone = rs.getString("phone");
	                users.add(new User(id, name, email, phone));
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return users;
	    }

	    public int getTotalUsersCount() {
	        int count = 0;
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_USERS_SQL);
	             ResultSet rs = preparedStatement.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt(1);
	            }
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	        return count;
	    }

	    public void updateUser(User user) {
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
	            preparedStatement.setString(1, user.getName());
	            preparedStatement.setString(2, user.getEmail());
	            preparedStatement.setString(3, user.getPhone());
	            preparedStatement.setInt(4, user.getId());
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }

	    public void deleteUser(int id) {
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)) {
	            preparedStatement.setInt(1, id);
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            printSQLException(e);
	        }
	    }

	    private void printSQLException(SQLException ex) {
	        for (Throwable e : ex) {
	            if (e instanceof SQLException) {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
}
