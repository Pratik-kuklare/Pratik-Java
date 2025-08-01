package com.election.controller;

import com.election.model.User;
import com.election.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello! The application is running successfully!";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       HttpSession session, Model model) {
        
        System.out.println("Login attempt - Username: " + username);
        
        try {
            if (userService.validateUser(username, password)) {
                User user = userService.findByUsername(username);
                session.setAttribute("user", user);
                
                System.out.println("Login successful - User: " + user.getUsername() + ", Role: " + user.getRole());
                
                if ("ADMIN".equals(user.getRole())) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/voter/dashboard";
                }
            } else {
                System.out.println("Login failed - Invalid credentials");
                model.addAttribute("error", "Invalid username or password");
                return "login";
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Login failed: " + e.getMessage());
            return "login";
        }
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            if (userService.registerUser(user)) {
                model.addAttribute("success", "Registration successful! Please login.");
                return "login";
            } else {
                model.addAttribute("error", "Username or National ID already exists");
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            e.printStackTrace(); // This will show the error in console
            return "register";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}