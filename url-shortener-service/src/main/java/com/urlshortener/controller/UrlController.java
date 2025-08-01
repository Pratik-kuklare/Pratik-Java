package com.urlshortener.controller;

import com.urlshortener.dto.ShortenUrlRequest;
import com.urlshortener.dto.ShortenUrlResponse;
import com.urlshortener.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class UrlController {
    
    private final UrlShortenerService urlShortenerService;

    public UrlController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/api/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@Valid @RequestBody ShortenUrlRequest request) {
        ShortenUrlResponse response = urlShortenerService.shortenUrl(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
    public void redirectToOriginalUrl(@PathVariable String shortCode, 
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");
        
        Optional<String> originalUrl = urlShortenerService.getOriginalUrl(
            shortCode, ipAddress, userAgent, referer);
        
        if (originalUrl.isPresent()) {
            response.sendRedirect(originalUrl.get());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short URL not found or expired");
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}