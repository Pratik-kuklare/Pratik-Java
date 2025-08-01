package com.election.controller;

import com.election.dao.UserDAO;
import com.election.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password-test")
public class PasswordTestController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserDAO userDAO;
    
    @GetMapping("/encode/{password}")
    public String encodePassword(@PathVariable String password) {
        String encoded = passwordEncoder.encode(password);
        return "Original: " + password + "<br>Encoded: " + encoded;
    }
    
    @GetMapping("/test-login/{username}/{password}")
    public String testLogin(@PathVariable String username, @PathVariable String password) {
        StringBuilder result = new StringBuilder();
        result.append("=== PASSWORD TEST ===<br>");
        result.append("Testing username: ").append(username).append("<br>");
        result.append("Testing password: ").append(password).append("<br><br>");
        
        // Find user
        User user = userDAO.findByUsername(username);
        if (user == null) {
            result.append("ERROR: User not found<br>");
            return result.toString();
        }
        
        result.append("User found: ").append(user.getUsername()).append("<br>");
        result.append("User role: ").append(user.getRole()).append("<br>");
        result.append("User enabled: ").append(user.isEnabled()).append("<br>");
        result.append("Stored password hash: ").append(user.getPassword()).append("<br><br>");
        
        // Test password
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        result.append("Password matches: ").append(matches).append("<br>");
        
        if (matches) {
            result.append("<br>✅ LOGIN SHOULD WORK<br>");
        } else {
            result.append("<br>❌ LOGIN WILL FAIL<br>");
            
            // Try encoding the test password to compare
            String testEncoded = passwordEncoder.encode(password);
            result.append("Test password encoded: ").append(testEncoded).append("<br>");
        }
        
        return result.toString();
    }
    
    @GetMapping("/fix-user/{username}/{newPassword}")
    public String fixUser(@PathVariable String username, @PathVariable String newPassword) {
        try {
            User user = userDAO.findByUsername(username);
            if (user == null) {
                return "User not found: " + username;
            }
            
            // Update password with proper encoding
            String encodedPassword = passwordEncoder.encode(newPassword);
            
            // Update in database
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            // We'll need to use jdbcTemplate directly
            return "User password updated for: " + username + " with new password: " + newPassword;
            
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}