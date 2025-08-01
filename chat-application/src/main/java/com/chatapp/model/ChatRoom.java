package com.chatapp.model;

public class ChatRoom {
    private int id;
    private String name;
    private boolean isPrivate;
    private int createdBy;
    
    public ChatRoom() {}
    
    public ChatRoom(int id, String name, boolean isPrivate, int createdBy) {
        this.id = id;
        this.name = name;
        this.isPrivate = isPrivate;
        this.createdBy = createdBy;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public boolean isPrivate() { return isPrivate; }
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }
    
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    
    @Override
    public String toString() {
        return name + (isPrivate ? " (Private)" : " (Group)");
    }
}