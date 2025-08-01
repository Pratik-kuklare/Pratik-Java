package com.urlshortener.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UrlAnalytics {
    private String shortCode;
    private String originalUrl;
    private Long totalClicks;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private List<DailyClickStats> dailyStats;

    public UrlAnalytics(String shortCode, String originalUrl, Long totalClicks, 
                       LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.totalClicks = totalClicks;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    // Getters and Setters
    public String getShortCode() { return shortCode; }
    public String getOriginalUrl() { return originalUrl; }
    public Long getTotalClicks() { return totalClicks; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public List<DailyClickStats> getDailyStats() { return dailyStats; }
    public void setDailyStats(List<DailyClickStats> dailyStats) { this.dailyStats = dailyStats; }

    public static class DailyClickStats {
        private String date;
        private Long clicks;

        public DailyClickStats(String date, Long clicks) {
            this.date = date;
            this.clicks = clicks;
        }

        public String getDate() { return date; }
        public Long getClicks() { return clicks; }
    }
}