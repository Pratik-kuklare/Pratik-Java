package dao;

import config.DatabaseConfig;
import models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (name, email, phone, address, user_type) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getUserType().toString());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY name";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserType(User.UserType.valueOf(rs.getString("user_type")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return users;
    }
    
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserType(User.UserType.valueOf(rs.getString("user_type")));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<User> searchUsers(String searchTerm) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE name LIKE ? OR email LIKE ? ORDER BY name";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserType(User.UserType.valueOf(rs.getString("user_type")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return users;
    }
}