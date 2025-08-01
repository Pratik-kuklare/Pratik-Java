package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CartService cartService;
    
    @GetMapping("/")
    public String home(Model model, HttpSession session,
                      @RequestParam(required = false) String category,
                      @RequestParam(required = false) String search) {
        
        List<Product> products;
        
        if (search != null && !search.trim().isEmpty()) {
            products = productService.searchProducts(search.trim());
            model.addAttribute("searchKeyword", search);
        } else if (category != null && !category.trim().isEmpty()) {
            products = productService.getProductsByCategory(category);
            model.addAttribute("selectedCategory", category);
        } else {
            products = productService.getAvailableProducts();
        }
        
        model.addAttribute("products", products);
        model.addAttribute("categories", productService.getAllCategories());
        
        // Add cart info to model
        String sessionId = session.getId();
        model.addAttribute("cartItemCount", cartService.getCartItemCount(sessionId));
        
        return "index";
    }
    
    @GetMapping("/product")
    public String productDetail(@RequestParam Long id, Model model, HttpSession session) {
        return productService.getProductById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("cartItemCount", cartService.getCartItemCount(session.getId()));
                    return "product-detail";
                })
                .orElse("redirect:/");
    }
}