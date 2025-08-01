package com.bookstore.controller;

import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping("/")
    public String home(Model model, 
                      @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "12") int size,
                      @RequestParam(required = false) String search) {
        
        try {
            var books = bookService.searchBooks(search, PageRequest.of(page, size));
            var categories = bookService.getAllCategories();
            
            model.addAttribute("books", books);
            model.addAttribute("categories", categories);
            model.addAttribute("currentSearch", search);
        } catch (Exception e) {
            // If database error, just show basic page
            model.addAttribute("error", "Database connection issue: " + e.getMessage());
        }
        
        return "index";
    }
    
    @GetMapping("/books")
    public String books(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "12") int size,
                       @RequestParam(required = false) String search,
                       @RequestParam(required = false) String category) {
        
        try {
            var books = bookService.searchBooks(search, PageRequest.of(page, size));
            var categories = bookService.getAllCategories();
            
            model.addAttribute("books", books);
            model.addAttribute("categories", categories);
            model.addAttribute("currentSearch", search);
            model.addAttribute("currentCategory", category);
        } catch (Exception e) {
            // If database error, show error message
            model.addAttribute("error", "Error loading books: " + e.getMessage());
        }
        
        return "books";
    }
}