package com.studentmanagement.controller;

import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/health")
public class HealthController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private MarkService markService;
    
    @GetMapping("/check")
    @ResponseBody
    public String healthCheck() {
        try {
            long studentCount = studentService.getAllStudents().size();
            long markCount = markService.getAllMarks().size();
            
            return "✅ Application is healthy!\n" +
                   "Students: " + studentCount + "\n" +
                   "Marks: " + markCount + "\n" +
                   "Database: Connected\n" +
                   "Status: Ready to use";
        } catch (Exception e) {
            return "❌ Application has issues:\n" + e.getMessage();
        }
    }
    
    @GetMapping("/status")
    public String statusPage(Model model) {
        try {
            model.addAttribute("studentCount", studentService.getAllStudents().size());
            model.addAttribute("markCount", markService.getAllMarks().size());
            model.addAttribute("status", "healthy");
            model.addAttribute("message", "Application is working properly!");
        } catch (Exception e) {
            model.addAttribute("studentCount", 0);
            model.addAttribute("markCount", 0);
            model.addAttribute("status", "error");
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "health-status";
    }
}