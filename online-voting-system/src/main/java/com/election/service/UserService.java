package com.election.service;

import com.election.dao.UserDAO;
import com.election.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public boolean registerUser(User user) {
        // Check if username or national ID already exists
        if (userDAO.existsByUsername(user.getUsername()) || 
            userDAO.existsByNationalId(user.getNationalId())) {
            return false;
        }
        
        // Set default values for new user
        user.setRole("VOTER");
        user.setEnabled(true);
        user.setHasVoted(false);
        user.setRegistrationDate(java.time.LocalDateTime.now());
        
        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        try {
            // Save user
            userDAO.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
    
    public User findByNationalId(String nationalId) {
        return userDAO.findByNationalId(nationalId);
    }
    
    public void markAsVoted(Long userId) {
        userDAO.updateVoteStatus(userId);
    }
    
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
    
    public boolean validateUser(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return false;
        }
        
        if (!user.isEnabled()) {
            return false;
        }
        
        // For admin user, check plain text password temporarily
        if ("admin".equals(username) && "admin123".equals(password)) {
            return true;
        }
        
        // For other users, try BCrypt first, then fallback to plain text
        try {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return true;
            }
        } catch (Exception e) {
            // BCrypt failed, try plain text comparison
        }
        
        // Fallback to plain text comparison
        return password.equals(user.getPassword());
    }
}