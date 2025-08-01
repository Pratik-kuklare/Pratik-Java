package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.Cart;
import com.bookstore.entity.CartItem;
import com.bookstore.entity.User;
import com.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private BookService bookService;
    
    public Cart getCartByUser(User user) {
        Optional<Cart> cart = cartRepository.findByUser(user);
        if (cart.isPresent()) {
            return cart.get();
        } else {
            Cart newCart = new Cart(user);
            return cartRepository.save(newCart);
        }
    }
    
    public void addToCart(User user, Long bookId, Integer quantity) {
        Cart cart = getCartByUser(user);
        Optional<Book> bookOpt = bookService.findBookById(bookId);
        
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            
            // Check if book already exists in cart
            Optional<CartItem> existingItem = cart.getItems().stream()
                    .filter(item -> item.getBook().getId().equals(bookId))
                    .findFirst();
            
            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                CartItem newItem = new CartItem(cart, book, quantity);
                cart.getItems().add(newItem);
            }
            
            cartRepository.save(cart);
        }
    }
    
    public void updateCartItem(User user, Long bookId, Integer quantity) {
        Cart cart = getCartByUser(user);
        
        Optional<CartItem> itemOpt = cart.getItems().stream()
                .filter(item -> item.getBook().getId().equals(bookId))
                .findFirst();
        
        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            if (quantity <= 0) {
                cart.getItems().remove(item);
            } else {
                item.setQuantity(quantity);
            }
            cartRepository.save(cart);
        }
    }
    
    public void removeFromCart(User user, Long bookId) {
        Cart cart = getCartByUser(user);
        cart.getItems().removeIf(item -> item.getBook().getId().equals(bookId));
        cartRepository.save(cart);
    }
    
    public void clearCart(User user) {
        Cart cart = getCartByUser(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}