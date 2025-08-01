package models;

public class User {
    private int userId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private UserType userType;
    
    public enum UserType {
        STUDENT, FACULTY, STAFF
    }
    
    public User() {}
    
    public User(String name, String email, String phone, String address, UserType userType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.userType = userType;
    }
    
    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }
    
    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}