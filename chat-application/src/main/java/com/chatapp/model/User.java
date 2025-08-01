package com.chatapp.model;

public class User {
    private int id;
    private String username;
    private String email;
    private boolean isOnline;
    
    public User() {}
    
    public User(int id, String username, String email, boolean isOnline) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.isOnline = isOnline;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public boolean isOnline() { return isOnline; }
    public void setOnline(boolean online) { isOnline = online; }
    
    @Override
    public String toString() {
        return username + (isOnline ? " (Online)" : " (Offline)");
    }
}