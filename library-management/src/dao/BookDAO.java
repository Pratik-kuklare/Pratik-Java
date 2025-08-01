package dao;

import config.DatabaseConfig;
import models.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    
    public boolean addBook(Book book) {
        String sql = "INSERT INTO books (title, author, isbn, category, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getCategory());
            stmt.setInt(5, book.getTotalCopies());
            stmt.setInt(6, book.getAvailableCopies());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY title";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return books;
    }
    
    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean updateAvailableCopies(int bookId, int newAvailableCount) {
        String sql = "UPDATE books SET available_copies = ? WHERE book_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, newAvailableCount);
            stmt.setInt(2, bookId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Book> searchBooks(String searchTerm) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ? ORDER BY title";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setIsbn(rs.getString("isbn"));
                book.setCategory(rs.getString("category"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return books;
    }
}