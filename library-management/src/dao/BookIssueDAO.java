package dao;

import config.DatabaseConfig;
import models.BookIssue;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookIssueDAO {
    private static final double LATE_FEE_PER_DAY = 2.0; // $2 per day
    
    public boolean issueBook(BookIssue issue) {
        String sql = "INSERT INTO book_issues (book_id, user_id, issue_date, due_date, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, issue.getBookId());
            stmt.setInt(2, issue.getUserId());
            stmt.setDate(3, Date.valueOf(issue.getIssueDate()));
            stmt.setDate(4, Date.valueOf(issue.getDueDate()));
            stmt.setString(5, issue.getStatus().toString());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean returnBook(int issueId) {
        LocalDate returnDate = LocalDate.now();
        double lateFee = calculateLateFee(issueId, returnDate);
        
        String sql = "UPDATE book_issues SET return_date = ?, late_fee = ?, status = 'RETURNED' WHERE issue_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(returnDate));
            stmt.setDouble(2, lateFee);
            stmt.setInt(3, issueId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<BookIssue> getAllIssues() {
        List<BookIssue> issues = new ArrayList<>();
        String sql = """
            SELECT bi.*, b.title as book_title, u.name as user_name 
            FROM book_issues bi 
            JOIN books b ON bi.book_id = b.book_id 
            JOIN users u ON bi.user_id = u.user_id 
            ORDER BY bi.issue_date DESC
            """;
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                BookIssue issue = createBookIssueFromResultSet(rs);
                issues.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return issues;
    }
    
    public List<BookIssue> getActiveIssues() {
        List<BookIssue> issues = new ArrayList<>();
        String sql = """
            SELECT bi.*, b.title as book_title, u.name as user_name 
            FROM book_issues bi 
            JOIN books b ON bi.book_id = b.book_id 
            JOIN users u ON bi.user_id = u.user_id 
            WHERE bi.status = 'ISSUED' OR bi.status = 'OVERDUE'
            ORDER BY bi.due_date ASC
            """;
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                BookIssue issue = createBookIssueFromResultSet(rs);
                // Update status if overdue
                if (issue.getDueDate().isBefore(LocalDate.now()) && 
                    issue.getStatus() == BookIssue.IssueStatus.ISSUED) {
                    issue.setStatus(BookIssue.IssueStatus.OVERDUE);
                    updateIssueStatus(issue.getIssueId(), BookIssue.IssueStatus.OVERDUE);
                }
                issues.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return issues;
    }
    
    public List<BookIssue> getOverdueIssues() {
        List<BookIssue> issues = new ArrayList<>();
        String sql = """
            SELECT bi.*, b.title as book_title, u.name as user_name 
            FROM book_issues bi 
            JOIN books b ON bi.book_id = b.book_id 
            JOIN users u ON bi.user_id = u.user_id 
            WHERE bi.due_date < CURDATE() AND bi.status != 'RETURNED'
            ORDER BY bi.due_date ASC
            """;
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                BookIssue issue = createBookIssueFromResultSet(rs);
                issue.setStatus(BookIssue.IssueStatus.OVERDUE);
                updateIssueStatus(issue.getIssueId(), BookIssue.IssueStatus.OVERDUE);
                issues.add(issue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return issues;
    }
    
    private BookIssue createBookIssueFromResultSet(ResultSet rs) throws SQLException {
        BookIssue issue = new BookIssue();
        issue.setIssueId(rs.getInt("issue_id"));
        issue.setBookId(rs.getInt("book_id"));
        issue.setUserId(rs.getInt("user_id"));
        issue.setBookTitle(rs.getString("book_title"));
        issue.setUserName(rs.getString("user_name"));
        issue.setIssueDate(rs.getDate("issue_date").toLocalDate());
        issue.setDueDate(rs.getDate("due_date").toLocalDate());
        
        Date returnDate = rs.getDate("return_date");
        if (returnDate != null) {
            issue.setReturnDate(returnDate.toLocalDate());
        }
        
        issue.setLateFee(rs.getDouble("late_fee"));
        issue.setStatus(BookIssue.IssueStatus.valueOf(rs.getString("status")));
        
        return issue;
    }
    
    private double calculateLateFee(int issueId, LocalDate returnDate) {
        String sql = "SELECT due_date FROM book_issues WHERE issue_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, issueId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                if (returnDate.isAfter(dueDate)) {
                    long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                    return daysLate * LATE_FEE_PER_DAY;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    private void updateIssueStatus(int issueId, BookIssue.IssueStatus status) {
        String sql = "UPDATE book_issues SET status = ? WHERE issue_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status.toString());
            stmt.setInt(2, issueId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}