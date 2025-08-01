package com.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/minimal")
public class MinimalTestController {
    
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "Minimal test works!");
        return "minimal-test";
    }
    
    @GetMapping("/form")
    public String showForm(Model model) {
        return "minimal-form";
    }
    
    @PostMapping("/form")
    public String submitForm(@RequestParam String name, 
                            @RequestParam String email,
                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", 
            "Form submitted successfully! Name: " + name + ", Email: " + email);
        return "redirect:/minimal/result";
    }
    
    @GetMapping("/result")
    public String result(Model model) {
        return "minimal-result";
    }
}