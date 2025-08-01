package com.example.ecommerce.service;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private ProductService productService;
    
    @Transactional
    public Order createOrder(String sessionId, String customerName, String customerEmail, String shippingAddress) {
        List<CartItem> cartItems = cartService.getCartItems(sessionId);
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        // Validate stock availability
        for (CartItem item : cartItems) {
            if (!productService.isProductAvailable(item.getProduct().getId(), item.getQuantity())) {
                throw new RuntimeException("Product " + item.getProduct().getName() + " is out of stock");
            }
        }
        
        // Create order
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setShippingAddress(shippingAddress);
        order.setTotalAmount(cartService.getCartTotal(sessionId));
        
        // Save order first to get ID
        Order savedOrder = orderRepository.save(order);
        
        // Create order items
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(
                        savedOrder,
                        cartItem.getProduct(),
                        cartItem.getQuantity(),
                        cartItem.getProduct().getPrice()
                ))
                .collect(Collectors.toList());
        
        savedOrder.setOrderItems(orderItems);
        
        // Update product stock
        for (CartItem item : cartItems) {
            productService.updateStock(item.getProduct().getId(), item.getQuantity());
        }
        
        // Clear cart
        cartService.clearCart(sessionId);
        
        return orderRepository.save(savedOrder);
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmailOrderByOrderDateDesc(email);
    }
    
    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found");
    }
    
    public boolean simulatePayment(BigDecimal amount) {
        // Simple payment simulation - always returns true
        // In real implementation, integrate with payment gateway
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
}