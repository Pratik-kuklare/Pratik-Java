package com.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;

@Controller
@RequestMapping("/working")
public class WorkingController {
    
    // Simple in-memory storage for testing
    private static List<Map<String, Object>> students = new ArrayList<>();
    private static List<Map<String, Object>> marks = new ArrayList<>();
    private static Long studentIdCounter = 1L;
    private static Long markIdCounter = 1L;
    
    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", students);
        return "working/students";
    }
    
    @GetMapping("/students/new")
    public String showStudentForm(Model model) {
        return "working/student-form";
    }
    
    @PostMapping("/students")
    public String createStudent(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam String phoneNumber,
                               @RequestParam String dateOfBirth,
                               @RequestParam String address,
                               @RequestParam String studentClass,
                               RedirectAttributes redirectAttributes) {
        
        Map<String, Object> student = new HashMap<>();
        student.put("id", studentIdCounter++);
        student.put("firstName", firstName);
        student.put("lastName", lastName);
        student.put("fullName", firstName + " " + lastName);
        student.put("email", email);
        student.put("phoneNumber", phoneNumber);
        student.put("dateOfBirth", dateOfBirth);
        student.put("address", address);
        student.put("studentClass", studentClass);
        
        students.add(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student added successfully!");
        return "redirect:/working/students";
    }
    
    @GetMapping("/marks/new")
    public String showMarkForm(Model model) {
        model.addAttribute("students", students);
        return "working/mark-form";
    }
    
    @PostMapping("/marks")
    public String createMark(@RequestParam(required = false) String studentId,
                            @RequestParam(required = false) String subject,
                            @RequestParam(required = false) String examType,
                            @RequestParam(required = false) String marksObtained,
                            @RequestParam(required = false) String totalMarks,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        
        try {
            // Validate and convert parameters
            if (studentId == null || studentId.trim().isEmpty()) {
                throw new RuntimeException("Please select a student");
            }
            if (subject == null || subject.trim().isEmpty()) {
                throw new RuntimeException("Subject is required");
            }
            if (examType == null || examType.trim().isEmpty()) {
                throw new RuntimeException("Exam type is required");
            }
            if (marksObtained == null || marksObtained.trim().isEmpty()) {
                throw new RuntimeException("Marks obtained is required");
            }
            if (totalMarks == null || totalMarks.trim().isEmpty()) {
                throw new RuntimeException("Total marks is required");
            }
            
            Long studentIdLong = Long.parseLong(studentId);
            Integer marksObtainedInt = Integer.parseInt(marksObtained);
            Integer totalMarksInt = Integer.parseInt(totalMarks);
            
            if (marksObtainedInt < 0) {
                throw new RuntimeException("Marks obtained cannot be negative");
            }
            if (totalMarksInt <= 0) {
                throw new RuntimeException("Total marks must be greater than 0");
            }
            if (marksObtainedInt > totalMarksInt) {
                throw new RuntimeException("Marks obtained cannot exceed total marks");
            }
            
            // Find student
            Map<String, Object> student = students.stream()
                .filter(s -> s.get("id").equals(studentIdLong))
                .findFirst()
                .orElse(null);
            
            if (student == null) {
                throw new RuntimeException("Student not found!");
            }
            
            Map<String, Object> mark = new HashMap<>();
            mark.put("id", markIdCounter++);
            mark.put("studentId", studentIdLong);
            mark.put("studentName", student.get("fullName"));
            mark.put("subject", subject.trim());
            mark.put("examType", examType.trim());
            mark.put("marksObtained", marksObtainedInt);
            mark.put("totalMarks", totalMarksInt);
            mark.put("percentage", (marksObtainedInt * 100.0) / totalMarksInt);
            
            // Calculate grade
            double percentage = (marksObtainedInt * 100.0) / totalMarksInt;
            String grade;
            if (percentage >= 90) grade = "A+";
            else if (percentage >= 80) grade = "A";
            else if (percentage >= 70) grade = "B";
            else if (percentage >= 60) grade = "C";
            else if (percentage >= 50) grade = "D";
            else grade = "F";
            mark.put("grade", grade);
            
            marks.add(mark);
            redirectAttributes.addFlashAttribute("successMessage", "Mark added successfully!");
            return "redirect:/working/marks";
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("students", students);
            return "working/mark-form";
        }
    }
    
    @GetMapping("/marks")
    public String listMarks(Model model) {
        model.addAttribute("marks", marks);
        return "working/marks";
    }
    
    @GetMapping("/reports")
    public String showReports(Model model) {
        model.addAttribute("students", students);
        return "working/reports";
    }
    
    @GetMapping("/reports/student")
    public String studentReport(@RequestParam(required = false) String studentId, Model model) {
        try {
            if (studentId == null || studentId.trim().isEmpty()) {
                throw new RuntimeException("Please select a student");
            }
            
            Long studentIdLong = Long.parseLong(studentId);
            
            // Find student
            Map<String, Object> student = students.stream()
                .filter(s -> s.get("id").equals(studentIdLong))
                .findFirst()
                .orElse(null);
            
            if (student == null) {
                throw new RuntimeException("Student not found!");
            }
            
            // Find marks for this student
            List<Map<String, Object>> studentMarks = marks.stream()
                .filter(m -> m.get("studentId").equals(studentIdLong))
                .collect(java.util.stream.Collectors.toList());
            
            // Calculate average
            double average = studentMarks.stream()
                .mapToDouble(m -> (Double) m.get("percentage"))
                .average()
                .orElse(0.0);
            
            model.addAttribute("student", student);
            model.addAttribute("marks", studentMarks);
            model.addAttribute("averagePercentage", average);
            return "working/student-report";
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("students", students);
            return "working/reports";
        }
    }
}