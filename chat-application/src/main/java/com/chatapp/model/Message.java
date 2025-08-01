package com.chatapp.model;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private int senderId;
    private String senderName;
    private int roomId;
    private String content;
    private LocalDateTime timestamp;
    private String status; // SENT, DELIVERED, READ
    
    public Message() {}
    
    public Message(int senderId, String senderName, int roomId, String content) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.roomId = roomId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.status = "SENT";
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }
    
    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}