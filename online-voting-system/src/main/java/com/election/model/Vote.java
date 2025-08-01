package com.election.model;

import java.time.LocalDateTime;

public class Vote {
    private Long id;
    private Long userId;
    private Long candidateId;
    private LocalDateTime voteTime;
    private String ipAddress;
    
    // Constructors
    public Vote() {}
    
    public Vote(Long userId, Long candidateId, String ipAddress) {
        this.userId = userId;
        this.candidateId = candidateId;
        this.ipAddress = ipAddress;
        this.voteTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }
    
    public LocalDateTime getVoteTime() { return voteTime; }
    public void setVoteTime(LocalDateTime voteTime) { this.voteTime = voteTime; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
}