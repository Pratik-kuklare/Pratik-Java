package com.bookstore.service;

import com.bookstore.entity.Order;
import com.bookstore.entity.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    
    public Payment processPayment(Order order, Payment.PaymentMethod paymentMethod) {
        Payment payment = new Payment(order, order.getTotalAmount(), paymentMethod);
        
        // Simulate payment processing
        boolean paymentSuccess = simulatePaymentProcessing(payment);
        
        if (paymentSuccess) {
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
            payment.setTransactionId(generateTransactionId());
            order.setStatus(Order.OrderStatus.CONFIRMED);
        } else {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            order.setStatus(Order.OrderStatus.CANCELLED);
        }
        
        return payment;
    }
    
    private boolean simulatePaymentProcessing(Payment payment) {
        // Simulate payment gateway integration
        // In real implementation, integrate with actual payment providers
        return Math.random() > 0.1; // 90% success rate for simulation
    }
    
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public Payment refundPayment(Payment payment) {
        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        return payment;
    }
}