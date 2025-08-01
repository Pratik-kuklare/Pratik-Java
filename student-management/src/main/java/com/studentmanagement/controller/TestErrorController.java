package com.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestErrorController {
    
    @GetMapping("/error")
    public String testError() {
        throw new RuntimeException("This is a test error to check error handling");
    }
    
    @GetMapping("/working")
    public String testWorking() {
        return "simple-test";
    }
}