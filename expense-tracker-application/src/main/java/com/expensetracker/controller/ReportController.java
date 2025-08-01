package com.expensetracker.controller;

import com.expensetracker.dto.ExpenseReportDto;
import com.expensetracker.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    private ReportService reportService;

    @GetMapping("/expense")
    public ResponseEntity<ExpenseReportDto> getExpenseReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ExpenseReportDto report = reportService.generateExpenseReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<ExpenseReportDto> getMonthlyReport(@PathVariable int year, 
                                                            @PathVariable int month) {
        ExpenseReportDto report = reportService.generateMonthlyReport(year, month);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/yearly/{year}")
    public ResponseEntity<ExpenseReportDto> getYearlyReport(@PathVariable int year) {
        ExpenseReportDto report = reportService.generateYearlyReport(year);
        return ResponseEntity.ok(report);
    }
}