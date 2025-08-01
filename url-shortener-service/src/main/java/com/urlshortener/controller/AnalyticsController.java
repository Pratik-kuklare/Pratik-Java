package com.urlshortener.controller;

import com.urlshortener.dto.UrlAnalytics;
import com.urlshortener.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {
    
    private final UrlShortenerService urlShortenerService;

    public AnalyticsController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<UrlAnalytics> getAnalytics(@PathVariable String shortCode) {
        Optional<UrlAnalytics> analytics = urlShortenerService.getAnalytics(shortCode);
        
        if (analytics.isPresent()) {
            return ResponseEntity.ok(analytics.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}