package com.bookstore.config;

import com.bookstore.entity.Book;
import com.bookstore.entity.User;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize sample books if database is empty
        if (bookRepository.count() == 0) {
            initializeBooks();
        }
        
        // Create admin user if not exists
        if (!userService.existsByUsername("admin")) {
            User admin = new User("admin", "admin@bookstore.com", "admin123");
            admin.setRole(User.Role.ADMIN);
            admin.setFirstName("Admin");
            admin.setLastName("User");
            userService.registerUser(admin);
        }
        
        // Create Pratik user if not exists
        if (!userService.existsByUsername("Pratik")) {
            User pratik = new User("Pratik", "pratik@bookstore.com", "9028574242");
            pratik.setRole(User.Role.ADMIN);
            pratik.setFirstName("Pratik");
            pratik.setLastName("User");
            userService.registerUser(pratik);
        }
    }
    
    private void initializeBooks() {
        Book[] books = {
            new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5", 
                    new BigDecimal("12.99"), 50),
            new Book("To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4", 
                    new BigDecimal("14.99"), 30),
            new Book("1984", "George Orwell", "978-0-452-28423-4", 
                    new BigDecimal("13.99"), 40),
            new Book("Pride and Prejudice", "Jane Austen", "978-0-14-143951-8", 
                    new BigDecimal("11.99"), 25),
            new Book("The Catcher in the Rye", "J.D. Salinger", "978-0-316-76948-0", 
                    new BigDecimal("15.99"), 35)
        };
        
        for (Book book : books) {
            book.setCategory("Fiction");
            book.setPublisher("Classic Publishers");
            book.setPublishedDate(LocalDate.of(2020, 1, 1));
            book.setDescription("A classic work of literature that has captivated readers for generations.");
            bookRepository.save(book);
        }
        
        // Add some technical books
        Book[] techBooks = {
            new Book("Clean Code", "Robert C. Martin", "978-0-13-235088-4", 
                    new BigDecimal("39.99"), 20),
            new Book("Spring Boot in Action", "Craig Walls", "978-1-61729-120-3", 
                    new BigDecimal("44.99"), 15),
            new Book("Effective Java", "Joshua Bloch", "978-0-13-468599-1", 
                    new BigDecimal("49.99"), 18)
        };
        
        for (Book book : techBooks) {
            book.setCategory("Technology");
            book.setPublisher("Tech Publishers");
            book.setPublishedDate(LocalDate.of(2021, 1, 1));
            book.setDescription("Essential reading for software developers and technology professionals.");
            bookRepository.save(book);
        }
    }
}