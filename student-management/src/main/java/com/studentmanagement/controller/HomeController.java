package com.studentmanagement.controller;

import com.studentmanagement.service.MarkService;
import com.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private MarkService markService;
    
    @GetMapping("/")
    public String home(Model model) {
        try {
            model.addAttribute("totalStudents", studentService.getAllStudents().size());
            model.addAttribute("totalMarks", markService.getAllMarks().size());
            model.addAttribute("totalClasses", studentService.getAllClasses().size());
            model.addAttribute("databaseConnected", true);
        } catch (Exception e) {
            model.addAttribute("totalStudents", 0);
            model.addAttribute("totalMarks", 0);
            model.addAttribute("totalClasses", 0);
            model.addAttribute("databaseConnected", false);
            model.addAttribute("databaseError", e.getMessage());
        }
        return "index";
    }
}