package net.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.ConnexionDb;
import net.java.model.User;

public class UserDAO {
    private static final String INSERT_USER_SQL = "INSERT INTO users (id, name, email, password, address, phoneNumber, photoUrl) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT id, name, email, password, isAdmin, address, phoneNumber, photoUrl FROM users WHERE id=?";
    private static final String SELECT_ALL_USERS = "SELECT id, name, email, password, isAdmin, address, phoneNumber, photoUrl FROM users";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id=?";
    private static final String UPDATE_USER_SQL = "UPDATE users SET name=?, email=?, password=?, address=?, phoneNumber=? WHERE id=?";

    public UserDAO() {
    }
    
    
    public User getUserById(String id) {
        User user = null;
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                // You might need to retrieve additional fields based on your User model
                user = new User(id, name, email, password, address, phoneNumber);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public void insertUser(User user) throws SQLException {
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setString(7, user.getPhotoUrl());
            preparedStatement.executeUpdate();
        }
    }

    public User selectUser(String id) {
        User user = null;
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
            	
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
            
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
        
                user = new User(id, name, email, password, address, phoneNumber);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                boolean isAdmin = rs.getBoolean("isAdmin");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                String photoUrl = rs.getString("photoUrl");
                users.add(new User(id, name, email, password, address, phoneNumber));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(String id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
            statement.setString(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getAddress()); 
            statement.setString(5, user.getPhoneNumber()); 
            statement.setString(6, user.getId()); 
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    
    
    /// function that take email,password and check existance and  of user   and check passwordcanthrow exception  
    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        String SELECT_USER_BY_EMAIL = "SELECT id, name, email, password, isAdmin, address, phoneNumber, photoUrl FROM users WHERE email=?";
        
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                // Email exists in the database, now check password
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    // Password matches, create User object
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    boolean isAdmin = rs.getBoolean("isAdmin");
                    String address = rs.getString("address");
                    String phoneNumber = rs.getString("phoneNumber");
                    String photoUrl = rs.getString("photoUrl");
                    user = new User(id, name, email, password, address, phoneNumber);
                } else {
                    // Password does not match, throw wrong password exception
                    throw new IllegalArgumentException("Wrong password");
                }
            } else {
                // Email does not exist in the database, throw email not found exception
                throw new IllegalArgumentException("Email not found");
            }
        } catch (SQLException e) {
            // Handle SQL Exception
            printSQLException(e);
        }
        return user;
    }

    private void printSQLException(SQLException ex) {
        // Your existing printSQLException method
    }
}
