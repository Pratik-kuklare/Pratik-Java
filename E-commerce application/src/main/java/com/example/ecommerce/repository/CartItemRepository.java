package com.example.ecommerce.repository;

import com.example.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findBySessionId(String sessionId);
    
    Optional<CartItem> findBySessionIdAndProductId(String sessionId, Long productId);
    
    @Modifying
    void deleteBySessionId(String sessionId);
}