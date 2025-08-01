package com.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ExpenseReportDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalAmount;
    private int transactionCount;
    private Map<String, BigDecimal> categoryBreakdown;
    private List<TransactionDto> transactions;

    // Constructors
    public ExpenseReportDto() {}

    public ExpenseReportDto(LocalDate startDate, LocalDate endDate, BigDecimal totalAmount, 
                           int transactionCount, Map<String, BigDecimal> categoryBreakdown) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.transactionCount = transactionCount;
        this.categoryBreakdown = categoryBreakdown;
    }

    // Getters and Setters
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public int getTransactionCount() { return transactionCount; }
    public void setTransactionCount(int transactionCount) { this.transactionCount = transactionCount; }

    public Map<String, BigDecimal> getCategoryBreakdown() { return categoryBreakdown; }
    public void setCategoryBreakdown(Map<String, BigDecimal> categoryBreakdown) { this.categoryBreakdown = categoryBreakdown; }

    public List<TransactionDto> getTransactions() { return transactions; }
    public void setTransactions(List<TransactionDto> transactions) { this.transactions = transactions; }
}