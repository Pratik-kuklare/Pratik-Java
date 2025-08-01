package com.election.model;

public class Candidate {
    private Long id;
    private String name;
    private String party;
    private String description;
    private String photoUrl;
    private boolean active;
    private int voteCount;
    
    // Constructors
    public Candidate() {}
    
    public Candidate(String name, String party, String description) {
        this.name = name;
        this.party = party;
        this.description = description;
        this.active = true;
        this.voteCount = 0;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
}