package com.studentmanagement.controller;

import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private MarkService markService;
    
    @GetMapping("/database")
    public String testDatabase(Model model) {
        try {
            // Test database connection
            long studentCount = studentService.getAllStudents().size();
            long markCount = markService.getAllMarks().size();
            
            model.addAttribute("studentCount", studentCount);
            model.addAttribute("markCount", markCount);
            model.addAttribute("databaseStatus", "Connected");
            model.addAttribute("message", "Database is working properly!");
            
        } catch (Exception e) {
            model.addAttribute("studentCount", 0);
            model.addAttribute("markCount", 0);
            model.addAttribute("databaseStatus", "Error");
            model.addAttribute("message", "Database error: " + e.getMessage());
            model.addAttribute("error", e.getClass().getSimpleName());
        }
        
        return "test-database";
    }
}