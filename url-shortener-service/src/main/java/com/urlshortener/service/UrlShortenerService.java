package com.urlshortener.service;

import com.urlshortener.dto.ShortenUrlRequest;
import com.urlshortener.dto.ShortenUrlResponse;
import com.urlshortener.dto.UrlAnalytics;
import com.urlshortener.entity.ClickEvent;
import com.urlshortener.entity.Url;
import com.urlshortener.repository.ClickEventRepository;
import com.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UrlShortenerService {
    
    private final UrlRepository urlRepository;
    private final ClickEventRepository clickEventRepository;
    private final String baseUrl;
    private final int shortCodeLength;
    
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public UrlShortenerService(UrlRepository urlRepository, 
                              ClickEventRepository clickEventRepository,
                              @Value("${app.base-url}") String baseUrl,
                              @Value("${app.short-code-length}") int shortCodeLength) {
        this.urlRepository = urlRepository;
        this.clickEventRepository = clickEventRepository;
        this.baseUrl = baseUrl;
        this.shortCodeLength = shortCodeLength;
    }

    @Transactional
    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request) {
        String shortCode = generateUniqueShortCode();
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(request.getExpirationDays());
        
        Url url = new Url(request.getUrl(), shortCode, expiresAt);
        url = urlRepository.save(url);
        
        String shortUrl = baseUrl + "/" + shortCode;
        
        return new ShortenUrlResponse(
            url.getOriginalUrl(),
            shortUrl,
            url.getShortCode(),
            url.getCreatedAt(),
            url.getExpiresAt()
        );
    }

    @Transactional
    public Optional<String> getOriginalUrl(String shortCode, String ipAddress, 
                                          String userAgent, String referer) {
        Optional<Url> urlOpt = urlRepository.findByShortCodeAndIsActiveTrue(shortCode);
        
        if (urlOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Url url = urlOpt.get();
        
        // Check if expired
        if (url.getExpiresAt().isBefore(LocalDateTime.now())) {
            url.setIsActive(false);
            urlRepository.save(url);
            return Optional.empty();
        }
        
        // Record click event
        ClickEvent clickEvent = new ClickEvent(url, ipAddress, userAgent, referer);
        clickEventRepository.save(clickEvent);
        
        // Increment click count
        urlRepository.incrementClickCount(url.getId());
        
        return Optional.of(url.getOriginalUrl());
    }

    public Optional<UrlAnalytics> getAnalytics(String shortCode) {
        Optional<Url> urlOpt = urlRepository.findByShortCode(shortCode);
        
        if (urlOpt.isEmpty()) {
            return Optional.empty();
        }
        
        Url url = urlOpt.get();
        
        UrlAnalytics analytics = new UrlAnalytics(
            url.getShortCode(),
            url.getOriginalUrl(),
            url.getClickCount(),
            url.getCreatedAt(),
            url.getExpiresAt()
        );
        
        // Get daily stats for last 30 days
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Object[]> dailyStats = clickEventRepository.findDailyClickStats(shortCode, thirtyDaysAgo);
        
        List<UrlAnalytics.DailyClickStats> stats = dailyStats.stream()
            .map(row -> new UrlAnalytics.DailyClickStats(
                row[0].toString(),
                ((Number) row[1]).longValue()
            ))
            .collect(Collectors.toList());
        
        analytics.setDailyStats(stats);
        
        return Optional.of(analytics);
    }

    private String generateUniqueShortCode() {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (urlRepository.existsByShortCode(shortCode));
        
        return shortCode;
    }

    private String generateShortCode() {
        StringBuilder sb = new StringBuilder(shortCodeLength);
        for (int i = 0; i < shortCodeLength; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}