package com.example.ecommerce.controller;

import com.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        String sessionId = session.getId();
        model.addAttribute("cartItems", cartService.getCartItems(sessionId));
        model.addAttribute("cartTotal", cartService.getCartTotal(sessionId));
        model.addAttribute("cartItemCount", cartService.getCartItemCount(sessionId));
        return "cart";
    }
    
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                           @RequestParam(defaultValue = "1") Integer quantity,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            cartService.addToCart(session.getId(), productId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Product added to cart successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }
    
    @PostMapping("/update")
    public String updateCartItem(@RequestParam Long cartItemId,
                                @RequestParam Integer quantity,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            cartService.updateCartItem(session.getId(), cartItemId, quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Cart updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/cart";
    }
    
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        try {
            cartService.removeFromCart(session.getId(), cartItemId);
            redirectAttributes.addFlashAttribute("successMessage", "Item removed from cart!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/cart";
    }
    
    @PostMapping("/clear")
    public String clearCart(HttpSession session, RedirectAttributes redirectAttributes) {
        cartService.clearCart(session.getId());
        redirectAttributes.addFlashAttribute("successMessage", "Cart cleared successfully!");
        return "redirect:/cart";
    }
}