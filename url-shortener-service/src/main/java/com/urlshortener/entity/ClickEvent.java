package com.urlshortener.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "click_events")
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", nullable = false)
    private Url url;
    
    @Column(nullable = false)
    private LocalDateTime clickedAt;
    
    @Column(length = 45)
    private String ipAddress;
    
    @Column(length = 500)
    private String userAgent;
    
    @Column(length = 500)
    private String referer;

    // Constructors
    public ClickEvent() {}
    
    public ClickEvent(Url url, String ipAddress, String userAgent, String referer) {
        this.url = url;
        this.clickedAt = LocalDateTime.now();
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.referer = referer;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Url getUrl() { return url; }
    public void setUrl(Url url) { this.url = url; }
    
    public LocalDateTime getClickedAt() { return clickedAt; }
    public void setClickedAt(LocalDateTime clickedAt) { this.clickedAt = clickedAt; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    
    public String getReferer() { return referer; }
    public void setReferer(String referer) { this.referer = referer; }
}