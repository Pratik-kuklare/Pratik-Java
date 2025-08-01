package com.expensetracker.config;

import com.expensetracker.entity.Category;
import com.expensetracker.entity.Transaction;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            initializeCategories();
            initializeSampleTransactions();
        }
    }

    private void initializeCategories() {
        Category food = new Category("Food", "Food and dining expenses");
        Category transport = new Category("Transportation", "Travel and transportation costs");
        Category entertainment = new Category("Entertainment", "Movies, games, and leisure activities");
        Category utilities = new Category("Utilities", "Electricity, water, internet bills");
        Category healthcare = new Category("Healthcare", "Medical expenses and insurance");
        Category shopping = new Category("Shopping", "Clothing and general purchases");

        categoryRepository.save(food);
        categoryRepository.save(transport);
        categoryRepository.save(entertainment);
        categoryRepository.save(utilities);
        categoryRepository.save(healthcare);
        categoryRepository.save(shopping);
    }

    private void initializeSampleTransactions() {
        Category food = categoryRepository.findByName("Food").orElse(null);
        Category transport = categoryRepository.findByName("Transportation").orElse(null);
        Category entertainment = categoryRepository.findByName("Entertainment").orElse(null);

        if (food != null) {
            Transaction lunch = new Transaction("Lunch at restaurant", new BigDecimal("25.50"), LocalDate.now().minusDays(1), food);
            Transaction groceries = new Transaction("Weekly groceries", new BigDecimal("85.75"), LocalDate.now().minusDays(3), food);
            transactionRepository.save(lunch);
            transactionRepository.save(groceries);
        }

        if (transport != null) {
            Transaction gas = new Transaction("Gas station", new BigDecimal("45.00"), LocalDate.now().minusDays(2), transport);
            transactionRepository.save(gas);
        }

        if (entertainment != null) {
            Transaction movie = new Transaction("Movie tickets", new BigDecimal("18.00"), LocalDate.now().minusDays(4), entertainment);
            transactionRepository.save(movie);
        }
    }
}