package com.urlshortener.dto;

import java.time.LocalDateTime;

public class ShortenUrlResponse {
    private String originalUrl;
    private String shortUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public ShortenUrlResponse(String originalUrl, String shortUrl, String shortCode, 
                             LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    // Getters
    public String getOriginalUrl() { return originalUrl; }
    public String getShortUrl() { return shortUrl; }
    public String getShortCode() { return shortCode; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
}