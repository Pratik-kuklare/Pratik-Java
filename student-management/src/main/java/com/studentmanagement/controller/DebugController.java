package com.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/debug")
public class DebugController {
    
    @GetMapping("/form")
    public String showDebugForm(Model model) {
        return "debug-form";
    }
    
    @PostMapping("/form")
    public String submitDebugForm(HttpServletRequest request, Model model) {
        try {
            // Get all parameters
            Map<String, String[]> params = request.getParameterMap();
            
            model.addAttribute("message", "Form submitted successfully!");
            model.addAttribute("parameters", params);
            
            // Log parameters for debugging
            System.out.println("=== DEBUG: Form Parameters ===");
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                System.out.println(entry.getKey() + " = " + Arrays.toString(entry.getValue()));
            }
            System.out.println("=== END DEBUG ===");
            
            return "debug-result";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            e.printStackTrace();
            return "debug-form";
        }
    }
}