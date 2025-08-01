package com.bookstore.service;

import com.bookstore.entity.*;
import com.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private PaymentService paymentService;
    
    public Order createOrder(User user, String shippingAddress) {
        Cart cart = cartService.getCartByUser(user);
        
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        // Validate stock availability
        for (CartItem item : cart.getItems()) {
            if (!bookService.isBookAvailable(item.getBook().getId(), item.getQuantity())) {
                throw new RuntimeException("Insufficient stock for book: " + item.getBook().getTitle());
            }
        }
        
        Order order = new Order(user, cart.getTotalPrice(), shippingAddress);
        order = orderRepository.save(order);
        
        // Create order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(order, cartItem.getBook(), 
                    cartItem.getQuantity(), cartItem.getBook().getPrice());
            orderItems.add(orderItem);
            
            // Update stock
            bookService.updateStock(cartItem.getBook().getId(), cartItem.getQuantity());
        }
        order.setOrderItems(orderItems);
        
        // Clear cart
        cartService.clearCart(user);
        
        return orderRepository.save(order);
    }
    
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }
    
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }
    
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}