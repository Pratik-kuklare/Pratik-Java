package com.election.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TestController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/test-db")
    public String testDatabase() {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            return "Database connection successful! Users count: " + count;
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
    
    @GetMapping("/test-users")
    public String testUsers() {
        try {
            String result = "Users in database:<br>";
            List<String> users = jdbcTemplate.query(
                "SELECT username, role, enabled FROM users", 
                (rs, rowNum) -> rs.getString("username") + " (" + rs.getString("role") + ", enabled: " + rs.getBoolean("enabled") + ")"
            );
            
            for (String user : users) {
                result += user + "<br>";
            }
            
            return result;
        } catch (Exception e) {
            return "Error fetching users: " + e.getMessage();
        }
    }
    
    @GetMapping("/test")
    public String test() {
        return "Application is running successfully!";
    }
}