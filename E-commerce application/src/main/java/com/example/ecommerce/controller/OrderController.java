package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CartService cartService;
    
    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        String sessionId = session.getId();
        model.addAttribute("cartItems", cartService.getCartItems(sessionId));
        model.addAttribute("cartTotal", cartService.getCartTotal(sessionId));
        model.addAttribute("cartItemCount", cartService.getCartItemCount(sessionId));
        
        if (cartService.getCartItems(sessionId).isEmpty()) {
            return "redirect:/cart";
        }
        
        return "checkout";
    }
    
    @PostMapping("/place")
    public String placeOrder(@RequestParam String customerName,
                            @RequestParam String customerEmail,
                            @RequestParam String shippingAddress,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        try {
            String sessionId = session.getId();
            
            // Simulate payment
            if (!orderService.simulatePayment(cartService.getCartTotal(sessionId))) {
                redirectAttributes.addFlashAttribute("errorMessage", "Payment failed. Please try again.");
                return "redirect:/order/checkout";
            }
            
            Order order = orderService.createOrder(sessionId, customerName, customerEmail, shippingAddress);
            redirectAttributes.addFlashAttribute("successMessage", "Order placed successfully! Order ID: " + order.getId());
            redirectAttributes.addFlashAttribute("orderId", order.getId());
            
            return "redirect:/order/confirmation";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/order/checkout";
        }
    }
    
    @GetMapping("/confirmation")
    public String orderConfirmation(Model model, HttpSession session) {
        model.addAttribute("cartItemCount", cartService.getCartItemCount(session.getId()));
        return "order-confirmation";
    }
    
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model, HttpSession session) {
        return orderService.getOrderById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("cartItemCount", cartService.getCartItemCount(session.getId()));
                    return "order-detail";
                })
                .orElse("redirect:/");
    }
}