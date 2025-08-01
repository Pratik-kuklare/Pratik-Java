package com.studentmanagement.controller;

import com.studentmanagement.entity.Student;
import com.studentmanagement.service.MarkService;
import com.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reports")
public class ReportController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private MarkService markService;
    
    @GetMapping
    public String reportsHome(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("classes", studentService.getAllClasses());
        return "reports/index";
    }
    
    @GetMapping("/student/{studentId}")
    public String studentReport(@PathVariable Long studentId, Model model) {
        Student student = studentService.getStudentById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        model.addAttribute("student", student);
        model.addAttribute("marks", markService.getMarksByStudentId(studentId));
        model.addAttribute("averagePercentage", markService.getAveragePercentageByStudentId(studentId));
        return "reports/student-report";
    }
    
    @GetMapping("/student")
    public String studentReportByParam(@RequestParam Long studentId, Model model) {
        return studentReport(studentId, model);
    }
    
    @GetMapping("/class")
    public String classReport(@RequestParam String studentClass, Model model) {
        model.addAttribute("students", studentService.getStudentsByClass(studentClass));
        model.addAttribute("selectedClass", studentClass);
        return "reports/class-report";
    }
}