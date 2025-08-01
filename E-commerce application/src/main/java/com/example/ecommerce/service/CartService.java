package com.example.ecommerce.service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductService productService;
    
    public List<CartItem> getCartItems(String sessionId) {
        return cartItemRepository.findBySessionId(sessionId);
    }
    
    @Transactional
    public void addToCart(String sessionId, Long productId, Integer quantity) {
        Optional<Product> productOpt = productService.getProductById(productId);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        Product product = productOpt.get();
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        
        Optional<CartItem> existingItem = cartItemRepository.findBySessionIdAndProductId(sessionId, productId);
        
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + quantity;
            if (product.getStockQuantity() < newQuantity) {
                throw new RuntimeException("Insufficient stock");
            }
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        } else {
            CartItem newItem = new CartItem(product, quantity, sessionId);
            cartItemRepository.save(newItem);
        }
    }
    
    @Transactional
    public void updateCartItem(String sessionId, Long cartItemId, Integer quantity) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);
        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if (!cartItem.getSessionId().equals(sessionId)) {
                throw new RuntimeException("Unauthorized access to cart item");
            }
            
            if (quantity <= 0) {
                cartItemRepository.delete(cartItem);
            } else {
                if (cartItem.getProduct().getStockQuantity() < quantity) {
                    throw new RuntimeException("Insufficient stock");
                }
                cartItem.setQuantity(quantity);
                cartItemRepository.save(cartItem);
            }
        }
    }
    
    @Transactional
    public void removeFromCart(String sessionId, Long cartItemId) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(cartItemId);
        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if (cartItem.getSessionId().equals(sessionId)) {
                cartItemRepository.delete(cartItem);
            }
        }
    }
    
    public BigDecimal getCartTotal(String sessionId) {
        List<CartItem> cartItems = getCartItems(sessionId);
        return cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public int getCartItemCount(String sessionId) {
        List<CartItem> cartItems = getCartItems(sessionId);
        return cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    
    @Transactional
    public void clearCart(String sessionId) {
        cartItemRepository.deleteBySessionId(sessionId);
    }
}