package com.bookstore.controller;

import com.bookstore.entity.User;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/reset-admin-password")
    public String resetAdminPassword() {
        User admin = userService.findByUsername("admin");
        if (admin != null) {
            admin.setPassword(passwordEncoder.encode("admin123"));
            userService.updateUser(admin);
            return "Admin password reset to 'admin123'";
        }
        return "Admin user not found";
    }
    
    @GetMapping("/check-password")
    public String checkPassword(@RequestParam String password) {
        User admin = userService.findByUsername("admin");
        if (admin != null) {
            boolean matches = passwordEncoder.matches(password, admin.getPassword());
            return "Password '" + password + "' matches: " + matches;
        }
        return "Admin user not found";
    }
    
    @GetMapping("/create-test-user")
    public String createTestUser() {
        if (!userService.existsByUsername("test")) {
            User testUser = new User("test", "test@test.com", "test123");
            testUser.setFirstName("Test");
            testUser.setLastName("User");
            userService.registerUser(testUser);
            return "Test user created: username=test, password=test123";
        }
        return "Test user already exists";
    }
    
    @GetMapping("/create-pratik-user")
    public String createPratikUser() {
        if (!userService.existsByUsername("Pratik")) {
            User pratikUser = new User("Pratik", "pratik@bookstore.com", "9028574242");
            pratikUser.setFirstName("Pratik");
            pratikUser.setLastName("User");
            pratikUser.setRole(User.Role.ADMIN);
            userService.registerUser(pratikUser);
            return "Pratik user created: username=Pratik, password=9028574242 (ADMIN)";
        }
        return "Pratik user already exists";
    }
    
    @GetMapping("/reset-pratik-password")
    public String resetPratikPassword() {
        User pratik = userService.findByUsername("Pratik");
        if (pratik != null) {
            pratik.setPassword(passwordEncoder.encode("9028574242"));
            userService.updateUser(pratik);
            return "Pratik password reset to '9028574242'";
        }
        return "Pratik user not found";
    }
}