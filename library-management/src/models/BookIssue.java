package models;

import java.time.LocalDate;

public class BookIssue {
    private int issueId;
    private int bookId;
    private int userId;
    private String bookTitle;
    private String userName;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double lateFee;
    private IssueStatus status;
    
    public enum IssueStatus {
        ISSUED, RETURNED, OVERDUE
    }
    
    public BookIssue() {}
    
    public BookIssue(int bookId, int userId, LocalDate issueDate, LocalDate dueDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = IssueStatus.ISSUED;
        this.lateFee = 0.0;
    }
    
    // Getters and Setters
    public int getIssueId() { return issueId; }
    public void setIssueId(int issueId) { this.issueId = issueId; }
    
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    
    public double getLateFee() { return lateFee; }
    public void setLateFee(double lateFee) { this.lateFee = lateFee; }
    
    public IssueStatus getStatus() { return status; }
    public void setStatus(IssueStatus status) { this.status = status; }
}