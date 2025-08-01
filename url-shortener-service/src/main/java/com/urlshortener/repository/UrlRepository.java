package com.urlshortener.repository;

import com.urlshortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    
    Optional<Url> findByShortCodeAndIsActiveTrue(String shortCode);
    
    Optional<Url> findByShortCode(String shortCode);
    
    boolean existsByShortCode(String shortCode);
    
    @Modifying
    @Query("UPDATE Url u SET u.clickCount = u.clickCount + 1 WHERE u.id = :id")
    void incrementClickCount(@Param("id") Long id);
    
    @Query("SELECT u FROM Url u WHERE u.expiresAt < :now AND u.isActive = true")
    java.util.List<Url> findExpiredUrls(@Param("now") LocalDateTime now);
}