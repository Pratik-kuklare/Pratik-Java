package com.expensetracker.service;

import com.expensetracker.dto.TransactionDto;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.Transaction;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDto> getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(this::convertToDto);
    }

    public List<TransactionDto> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactionsByCategory(Long categoryId) {
        return transactionRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Category category = categoryRepository.findById(transactionDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + transactionDto.getCategoryId()));
        
        Transaction transaction = convertToEntity(transactionDto, category);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDto(savedTransaction);
    }

    public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
        Transaction existingTransaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        Category category = categoryRepository.findById(transactionDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + transactionDto.getCategoryId()));

        existingTransaction.setDescription(transactionDto.getDescription());
        existingTransaction.setAmount(transactionDto.getAmount());
        existingTransaction.setTransactionDate(transactionDto.getTransactionDate());
        existingTransaction.setCategory(category);
        
        Transaction updatedTransaction = transactionRepository.save(existingTransaction);
        return convertToDto(updatedTransaction);
    }

    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }

    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setCategoryId(transaction.getCategory().getId());
        dto.setCategoryName(transaction.getCategory().getName());
        return dto;
    }

    private Transaction convertToEntity(TransactionDto dto, Category category) {
        Transaction transaction = new Transaction();
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setCategory(category);
        return transaction;
    }
}