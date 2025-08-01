package com.bookstore.controller;

import com.bookstore.entity.Order;
import com.bookstore.entity.Payment;
import com.bookstore.entity.User;
import com.bookstore.service.OrderService;
import com.bookstore.service.PaymentService;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String viewOrders(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        User user = userService.findByUsername(authentication.getName());
        var orders = orderService.getOrdersByUser(user);
        
        model.addAttribute("orders", orders);
        return "orders/list";
    }
    
    @GetMapping("/checkout")
    public String checkout(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("paymentMethods", Payment.PaymentMethod.values());
        
        return "orders/checkout";
    }
    
    @PostMapping("/place")
    public String placeOrder(@RequestParam String shippingAddress,
                            @RequestParam Payment.PaymentMethod paymentMethod,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        
        if (authentication == null) {
            return "redirect:/login";
        }
        
        try {
            User user = userService.findByUsername(authentication.getName());
            Order order = orderService.createOrder(user, shippingAddress);
            
            Payment payment = paymentService.processPayment(order, paymentMethod);
            
            if (payment.getStatus() == Payment.PaymentStatus.COMPLETED) {
                redirectAttributes.addFlashAttribute("success", 
                    "Order placed successfully! Order ID: " + order.getId());
                return "redirect:/orders/" + order.getId();
            } else {
                redirectAttributes.addFlashAttribute("error", "Payment failed. Please try again.");
                return "redirect:/cart";
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
    }
    
    @GetMapping("/{orderId}")
    public String viewOrder(@PathVariable Long orderId,
                           Model model,
                           Authentication authentication) {
        
        if (authentication == null) {
            return "redirect:/login";
        }
        
        var orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
            return "orders/detail";
        }
        
        return "redirect:/orders";
    }
}