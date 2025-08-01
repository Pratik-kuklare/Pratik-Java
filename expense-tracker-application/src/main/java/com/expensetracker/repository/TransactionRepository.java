package com.expensetracker.repository;

import com.expensetracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Transaction> findByCategoryId(Long categoryId);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalAmountBetweenDates(@Param("startDate") LocalDate startDate, 
                                         @Param("endDate") LocalDate endDate);
    
    @Query("SELECT c.name, SUM(t.amount) FROM Transaction t JOIN t.category c " +
           "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
           "GROUP BY c.name")
    List<Object[]> getCategoryBreakdownBetweenDates(@Param("startDate") LocalDate startDate, 
                                                    @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionDate BETWEEN :startDate AND :endDate")
    int getTransactionCountBetweenDates(@Param("startDate") LocalDate startDate, 
                                       @Param("endDate") LocalDate endDate);
}