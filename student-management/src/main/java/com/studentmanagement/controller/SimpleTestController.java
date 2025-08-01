package com.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simple")
public class SimpleTestController {
    
    @GetMapping("/test")
    public String simpleTest(Model model) {
        model.addAttribute("message", "Simple test is working!");
        model.addAttribute("timestamp", java.time.LocalDateTime.now());
        return "simple-test";
    }
}