package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    
    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
    
    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }
    
    public Page<Book> searchBooks(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return bookRepository.findAll(pageable);
        }
        return bookRepository.findByKeyword(keyword.trim(), pageable);
    }
    
    public List<Book> findBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }
    
    public List<String> getAllCategories() {
        return bookRepository.findAllCategories();
    }
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    
    public boolean isBookAvailable(Long bookId, Integer quantity) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.isPresent() && book.get().getStockQuantity() >= quantity;
    }
    
    public void updateStock(Long bookId, Integer quantity) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setStockQuantity(book.getStockQuantity() - quantity);
            bookRepository.save(book);
        }
    }
}