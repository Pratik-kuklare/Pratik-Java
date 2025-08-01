package com.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ShortenUrlRequest {
    @NotBlank(message = "URL is required")
    @Pattern(regexp = "^https?://.*", message = "URL must start with http:// or https://")
    private String url;
    
    private Integer expirationDays = 30; // Default 30 days

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public Integer getExpirationDays() { return expirationDays; }
    public void setExpirationDays(Integer expirationDays) { this.expirationDays = expirationDays; }
}