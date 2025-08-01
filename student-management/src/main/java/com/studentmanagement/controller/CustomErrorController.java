package com.studentmanagement.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        
        // Provide default values if attributes are null
        if (statusCode == null) {
            statusCode = 500;
        }
        
        if (errorMessage == null || errorMessage.trim().isEmpty()) {
            errorMessage = "An unexpected error occurred";
        }
        
        // Log the error for debugging
        System.out.println("=== ERROR DETAILS ===");
        System.out.println("Status Code: " + statusCode);
        System.out.println("Error Message: " + errorMessage);
        System.out.println("Request URI: " + requestUri);
        System.out.println("Exception: " + (exception != null ? exception.getClass().getSimpleName() : "None"));
        if (exception != null) {
            System.out.println("Exception Message: " + exception.getMessage());
            exception.printStackTrace();
        }
        
        // Check all request attributes for debugging
        System.out.println("--- All Request Attributes ---");
        java.util.Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = request.getAttribute(attributeName);
            System.out.println(attributeName + " = " + attributeValue);
        }
        System.out.println("=====================");
        
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("exception", exception);
        model.addAttribute("requestUri", requestUri);
        
        return "error";
    }
}