package com.bookstore.controller;

import com.bookstore.entity.User;
import com.bookstore.service.CartService;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String viewCart(Model model, HttpSession session, Authentication authentication) {
        // Check if user is authenticated
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }
        
        try {
            User user = userService.findByUsername(authentication.getName());
            if (user != null) {
                var cart = cartService.getCartByUser(user);
                model.addAttribute("cart", cart);
            } else {
                model.addAttribute("error", "User not found. Please login again.");
                return "redirect:/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error loading cart: " + e.getMessage());
        }
        
        return "cart/view";
    }
    
    @PostMapping("/add")
    public String addToCart(@RequestParam Long bookId,
                           @RequestParam(defaultValue = "1") Integer quantity,
                           @RequestParam(required = false) String returnUrl,
                           HttpSession session,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        
        try {
            // Check if user is authenticated
            if (authentication != null && authentication.isAuthenticated() && 
                !authentication.getName().equals("anonymousUser")) {
                
                User user = userService.findByUsername(authentication.getName());
                if (user != null) {
                    cartService.addToCart(user, bookId, quantity);
                    redirectAttributes.addFlashAttribute("success", "Book added to cart!");
                } else {
                    redirectAttributes.addFlashAttribute("error", "User not found. Please login again.");
                    return "redirect:/login";
                }
            } else {
                // For non-authenticated users, redirect to login
                redirectAttributes.addFlashAttribute("info", "Please login to add items to cart.");
                return "redirect:/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding book to cart: " + e.getMessage());
        }
        
        // Return to books page
        return "redirect:/books";
    }
    
    @PostMapping("/update")
    public String updateCart(@RequestParam Long bookId,
                            @RequestParam Integer quantity,
                            HttpSession session,
                            Authentication authentication) {
        
        // Check if user is authenticated
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }
        
        try {
            User user = userService.findByUsername(authentication.getName());
            if (user != null) {
                cartService.updateCartItem(user, bookId, quantity);
            }
        } catch (Exception e) {
            // Handle error silently for update
        }
        
        return "redirect:/cart";
    }
    
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long bookId,
                                HttpSession session,
                                Authentication authentication) {
        
        // Check if user is authenticated
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }
        
        try {
            User user = userService.findByUsername(authentication.getName());
            if (user != null) {
                cartService.removeFromCart(user, bookId);
            }
        } catch (Exception e) {
            // Handle error silently for remove
        }
        
        return "redirect:/cart";
    }
    

}