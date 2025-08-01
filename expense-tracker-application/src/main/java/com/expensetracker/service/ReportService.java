package com.expensetracker.service;

import com.expensetracker.dto.ExpenseReportDto;
import com.expensetracker.dto.TransactionDto;
import com.expensetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private TransactionService transactionService;

    public ExpenseReportDto generateExpenseReport(LocalDate startDate, LocalDate endDate) {
        ExpenseReportDto report = new ExpenseReportDto();
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        
        // Get total amount
        BigDecimal totalAmount = transactionRepository.getTotalAmountBetweenDates(startDate, endDate);
        report.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);
        
        // Get transaction count
        int transactionCount = transactionRepository.getTransactionCountBetweenDates(startDate, endDate);
        report.setTransactionCount(transactionCount);
        
        // Get category breakdown
        List<Object[]> categoryData = transactionRepository.getCategoryBreakdownBetweenDates(startDate, endDate);
        Map<String, BigDecimal> categoryBreakdown = new HashMap<>();
        for (Object[] data : categoryData) {
            String categoryName = (String) data[0];
            BigDecimal amount = (BigDecimal) data[1];
            categoryBreakdown.put(categoryName, amount);
        }
        report.setCategoryBreakdown(categoryBreakdown);
        
        // Get transactions
        List<TransactionDto> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        report.setTransactions(transactions);
        
        return report;
    }

    public ExpenseReportDto generateMonthlyReport(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return generateExpenseReport(startDate, endDate);
    }

    public ExpenseReportDto generateYearlyReport(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return generateExpenseReport(startDate, endDate);
    }
}