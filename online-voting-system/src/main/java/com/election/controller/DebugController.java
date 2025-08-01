package com.election.controller;

import com.election.dao.UserDAO;
import com.election.model.User;
import com.election.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debug")
public class DebugController {
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/check-user/{username}")
    public String checkUser(@PathVariable String username) {
        StringBuilder result = new StringBuilder();
        result.append("=== USER CHECK DEBUG ===<br>");
        result.append("Checking username: ").append(username).append("<br><br>");
        
        // Check if username exists
        boolean usernameExists = userDAO.existsByUsername(username);
        result.append("Username exists: ").append(usernameExists).append("<br>");
        
        // Try to find user
        User user = userDAO.findByUsername(username);
        if (user != null) {
            result.append("User found: ").append(user.getUsername()).append("<br>");
            result.append("Role: ").append(user.getRole()).append("<br>");
            result.append("Enabled: ").append(user.isEnabled()).append("<br>");
            result.append("Email: ").append(user.getEmail()).append("<br>");
            result.append("National ID: ").append(user.getNationalId()).append("<br>");
        } else {
            result.append("User NOT found in database<br>");
        }
        
        return result.toString();
    }
    
    @GetMapping("/check-national-id/{nationalId}")
    public String checkNationalId(@PathVariable String nationalId) {
        StringBuilder result = new StringBuilder();
        result.append("=== NATIONAL ID CHECK DEBUG ===<br>");
        result.append("Checking National ID: ").append(nationalId).append("<br><br>");
        
        boolean exists = userDAO.existsByNationalId(nationalId);
        result.append("National ID exists: ").append(exists).append("<br>");
        
        User user = userDAO.findByNationalId(nationalId);
        if (user != null) {
            result.append("User with this National ID: ").append(user.getUsername()).append("<br>");
        } else {
            result.append("No user found with this National ID<br>");
        }
        
        return result.toString();
    }
    
    @PostMapping("/test-register")
    public String testRegister(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String email,
                              @RequestParam String fullName,
                              @RequestParam String nationalId) {
        
        StringBuilder result = new StringBuilder();
        result.append("=== REGISTRATION DEBUG ===<br>");
        result.append("Attempting to register:<br>");
        result.append("Username: ").append(username).append("<br>");
        result.append("Email: ").append(email).append("<br>");
        result.append("Full Name: ").append(fullName).append("<br>");
        result.append("National ID: ").append(nationalId).append("<br><br>");
        
        try {
            // Check if username exists
            boolean usernameExists = userDAO.existsByUsername(username);
            result.append("Username exists: ").append(usernameExists).append("<br>");
            
            // Check if national ID exists
            boolean nationalIdExists = userDAO.existsByNationalId(nationalId);
            result.append("National ID exists: ").append(nationalIdExists).append("<br>");
            
            if (usernameExists || nationalIdExists) {
                result.append("<br>REGISTRATION FAILED: User already exists<br>");
                return result.toString();
            }
            
            // Create user
            User user = new User(username, password, email, fullName, nationalId);
            result.append("User object created successfully<br>");
            
            // Encrypt password
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
            result.append("Password encrypted successfully<br>");
            
            // Save user
            userDAO.save(user);
            result.append("User saved to database successfully<br>");
            
            // Verify user was saved
            User savedUser = userDAO.findByUsername(username);
            if (savedUser != null) {
                result.append("Verification: User found in database after save<br>");
                result.append("Saved user ID: ").append(savedUser.getId()).append("<br>");
            } else {
                result.append("ERROR: User not found in database after save<br>");
            }
            
            result.append("<br>REGISTRATION SUCCESSFUL!<br>");
            
        } catch (Exception e) {
            result.append("<br>ERROR during registration: ").append(e.getMessage()).append("<br>");
            result.append("Stack trace: ").append(e.toString()).append("<br>");
        }
        
        return result.toString();
    }
    
    @PostMapping("/test-login")
    public String testLogin(@RequestParam String username,
                           @RequestParam String password) {
        
        StringBuilder result = new StringBuilder();
        result.append("=== LOGIN DEBUG ===<br>");
        result.append("Attempting to login:<br>");
        result.append("Username: ").append(username).append("<br><br>");
        
        try {
            // Find user
            User user = userDAO.findByUsername(username);
            if (user == null) {
                result.append("LOGIN FAILED: User not found<br>");
                return result.toString();
            }
            
            result.append("User found: ").append(user.getUsername()).append("<br>");
            result.append("Role: ").append(user.getRole()).append("<br>");
            result.append("Enabled: ").append(user.isEnabled()).append("<br>");
            
            if (!user.isEnabled()) {
                result.append("LOGIN FAILED: User is disabled<br>");
                return result.toString();
            }
            
            // Check password
            boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());
            result.append("Password match: ").append(passwordMatch).append("<br>");
            
            if (passwordMatch) {
                result.append("<br>LOGIN SUCCESSFUL!<br>");
                result.append("User should be redirected to: ");
                if ("ADMIN".equals(user.getRole())) {
                    result.append("/admin/dashboard<br>");
                } else {
                    result.append("/voter/dashboard<br>");
                }
            } else {
                result.append("<br>LOGIN FAILED: Invalid password<br>");
            }
            
        } catch (Exception e) {
            result.append("<br>ERROR during login: ").append(e.getMessage()).append("<br>");
        }
        
        return result.toString();
    }
}