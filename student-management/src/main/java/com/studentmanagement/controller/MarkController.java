package com.studentmanagement.controller;

import com.studentmanagement.entity.Mark;
import com.studentmanagement.entity.Student;
import com.studentmanagement.service.MarkService;
import com.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/marks")
public class MarkController {
    
    @Autowired
    private MarkService markService;
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public String listMarks(Model model) {
        try {
            System.out.println("=== DEBUG: Loading all marks ===");
            java.util.List<Mark> marks = markService.getAllMarks();
            System.out.println("Found " + marks.size() + " marks in database");
            
            model.addAttribute("marks", marks);
            
            if (marks.isEmpty()) {
                model.addAttribute("infoMessage", "No marks found. Add some marks first!");
            }
            
            return "marks/list";
        } catch (Exception e) {
            System.err.println("Error loading marks: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error loading marks: " + e.getMessage());
            model.addAttribute("marks", java.util.Collections.emptyList());
            return "marks/list";
        }
    }
    
    @GetMapping("/student/{studentId}")
    public String listMarksByStudent(@PathVariable Long studentId, Model model) {
        try {
            System.out.println("=== DEBUG: Loading marks for student ID: " + studentId + " ===");
            
            Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
            
            System.out.println("Found student: " + student.getFullName());
            
            java.util.List<Mark> marks = markService.getMarksByStudentId(studentId);
            System.out.println("Found " + marks.size() + " marks for student: " + student.getFullName());
            
            model.addAttribute("student", student);
            model.addAttribute("marks", marks);
            
            if (marks.isEmpty()) {
                model.addAttribute("infoMessage", "No marks found for " + student.getFullName() + ". Add some marks first!");
            }
            
            return "marks/student-marks";
        } catch (Exception e) {
            System.err.println("Error loading student marks: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error loading student marks: " + e.getMessage());
            return "error";
        }
    }
    
    @GetMapping("/new")
    public String showCreateForm(@RequestParam(required = false) Long studentId, Model model) {
        try {
            Mark mark = new Mark();
            if (studentId != null) {
                Student student = studentService.getStudentById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
                mark.setStudent(student);
            }
            
            model.addAttribute("mark", mark);
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("subjects", markService.getAllSubjects());
            model.addAttribute("examTypes", markService.getAllExamTypes());
            return "marks/form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error loading form: " + e.getMessage());
            model.addAttribute("mark", new Mark());
            model.addAttribute("students", java.util.Collections.emptyList());
            model.addAttribute("subjects", java.util.Collections.emptyList());
            model.addAttribute("examTypes", java.util.Collections.emptyList());
            return "marks/form";
        }
    }
    
    @PostMapping
    public String createMark(@RequestParam(required = false) String studentId,
                            @RequestParam(required = false) String subject,
                            @RequestParam(required = false) String examType,
                            @RequestParam(required = false) String marksObtained,
                            @RequestParam(required = false) String totalMarks,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        try {
            System.out.println("=== DEBUG: Creating Mark ===");
            System.out.println("Student ID: " + studentId);
            System.out.println("Subject: " + subject);
            System.out.println("Exam Type: " + examType);
            System.out.println("Marks Obtained: " + marksObtained);
            System.out.println("Total Marks: " + totalMarks);
            
            // Validate required fields
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
            
            // Convert and validate parameters
            Long studentIdLong = Long.parseLong(studentId.trim());
            Integer marksObtainedInt = Integer.parseInt(marksObtained.trim());
            Integer totalMarksInt = Integer.parseInt(totalMarks.trim());
            
            System.out.println("Converted - Student ID: " + studentIdLong + ", Marks: " + marksObtainedInt + "/" + totalMarksInt);
            
            // Validate business rules
            if (marksObtainedInt < 0) {
                throw new RuntimeException("Marks obtained cannot be negative");
            }
            if (totalMarksInt <= 0) {
                throw new RuntimeException("Total marks must be greater than 0");
            }
            if (marksObtainedInt > totalMarksInt) {
                throw new RuntimeException("Marks obtained cannot exceed total marks");
            }
            
            // Get the student
            Student student = studentService.getStudentById(studentIdLong)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentIdLong));
            
            System.out.println("Found student: " + student.getFullName());
            
            // Create and save mark
            Mark mark = new Mark();
            mark.setStudent(student);
            mark.setSubject(subject.trim());
            mark.setExamType(examType.trim());
            mark.setMarksObtained(marksObtainedInt);
            mark.setTotalMarks(totalMarksInt);
            
            System.out.println("Saving mark for: " + student.getFullName() + " - " + subject.trim());
            markService.saveMark(mark);
            System.out.println("Mark saved successfully!");
            
            redirectAttributes.addFlashAttribute("successMessage", "Mark added successfully for " + student.getFullName() + "!");
            return "redirect:/marks/student/" + studentIdLong;
            
        } catch (NumberFormatException e) {
            System.err.println("Number format error: " + e.getMessage());
            model.addAttribute("errorMessage", "Please enter valid numbers for marks. Error: " + e.getMessage());
            model.addAttribute("mark", new Mark());
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("subjects", markService.getAllSubjects());
            model.addAttribute("examTypes", markService.getAllExamTypes());
            return "marks/form";
        } catch (Exception e) {
            System.err.println("Error creating mark: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("mark", new Mark());
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("subjects", markService.getAllSubjects());
            model.addAttribute("examTypes", markService.getAllExamTypes());
            return "marks/form";
        }
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Mark mark = markService.getMarkById(id)
            .orElseThrow(() -> new RuntimeException("Mark not found"));
        
        model.addAttribute("mark", mark);
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("subjects", markService.getAllSubjects());
        model.addAttribute("examTypes", markService.getAllExamTypes());
        return "marks/form";
    }
    
    @PostMapping("/{id}")
    public String updateMark(@PathVariable Long id,
                            @RequestParam String studentId,
                            @RequestParam String subject,
                            @RequestParam String examType,
                            @RequestParam String marksObtained,
                            @RequestParam String totalMarks,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        
        try {
            // Convert and validate parameters
            Long studentIdLong = Long.parseLong(studentId);
            Integer marksObtainedInt = Integer.parseInt(marksObtained);
            Integer totalMarksInt = Integer.parseInt(totalMarks);
            
            // Validate business rules
            if (marksObtainedInt < 0) {
                throw new RuntimeException("Marks obtained cannot be negative");
            }
            if (totalMarksInt <= 0) {
                throw new RuntimeException("Total marks must be greater than 0");
            }
            if (marksObtainedInt > totalMarksInt) {
                throw new RuntimeException("Marks obtained cannot exceed total marks");
            }
            
            // Get the student
            Student student = studentService.getStudentById(studentIdLong)
                .orElseThrow(() -> new RuntimeException("Student not found"));
            
            // Get existing mark and update
            Mark mark = markService.getMarkById(id)
                .orElseThrow(() -> new RuntimeException("Mark not found"));
            
            mark.setStudent(student);
            mark.setSubject(subject.trim());
            mark.setExamType(examType.trim());
            mark.setMarksObtained(marksObtainedInt);
            mark.setTotalMarks(totalMarksInt);
            
            markService.saveMark(mark);
            redirectAttributes.addFlashAttribute("successMessage", "Mark updated successfully!");
            return "redirect:/marks/student/" + studentIdLong;
            
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Please enter valid numbers for marks");
            Mark mark = markService.getMarkById(id).orElse(new Mark());
            model.addAttribute("mark", mark);
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("subjects", markService.getAllSubjects());
            model.addAttribute("examTypes", markService.getAllExamTypes());
            return "marks/form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            Mark mark = markService.getMarkById(id).orElse(new Mark());
            model.addAttribute("mark", mark);
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("subjects", markService.getAllSubjects());
            model.addAttribute("examTypes", markService.getAllExamTypes());
            return "marks/form";
        }
    }
    
    @PostMapping("/{id}/delete")
    public String deleteMark(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Mark mark = markService.getMarkById(id)
            .orElseThrow(() -> new RuntimeException("Mark not found"));
        Long studentId = mark.getStudent().getId();
        
        markService.deleteMark(id);
        redirectAttributes.addFlashAttribute("successMessage", "Mark deleted successfully!");
        return "redirect:/marks/student/" + studentId;
    }
}