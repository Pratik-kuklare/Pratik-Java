package com.example.ecommerce.controller;

import com.example.ecommerce.config.DataInitializer;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private DataInitializer dataInitializer;
    
    @GetMapping("/reset-products")
    public String resetProducts(RedirectAttributes redirectAttributes) {
        try {
            // Clear cart items first to avoid foreign key constraint issues
            cartItemRepository.deleteAll();
            
            // Clear existing products
            productRepository.deleteAll();
            
            // Reload products with new data
            dataInitializer.run();
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Products have been reset with new images and Indian Rupee prices!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error resetting products: " + e.getMessage());
        }
        
        return "redirect:/";
    }
}