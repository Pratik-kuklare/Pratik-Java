package com.studentmanagement.controller;

import com.studentmanagement.entity.Student;
import com.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public String listStudents(@RequestParam(required = false) String search,
                              @RequestParam(required = false) String studentClass,
                              Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("students", studentService.searchStudents(search));
            model.addAttribute("search", search);
        } else if (studentClass != null && !studentClass.trim().isEmpty()) {
            model.addAttribute("students", studentService.getStudentsByClass(studentClass));
            model.addAttribute("selectedClass", studentClass);
        } else {
            model.addAttribute("students", studentService.getAllStudents());
        }
        
        model.addAttribute("classes", studentService.getAllClasses());
        return "students/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }
    
    @PostMapping
    public String createStudent(@Valid @ModelAttribute Student student,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/form";
        }
        
        if (studentService.existsByEmail(student.getEmail())) {
            result.rejectValue("email", "error.student", "Email already exists");
            return "students/form";
        }
        
        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student created successfully!");
        return "redirect:/students";
    }
    
    @GetMapping("/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        return "students/view";
    }
    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        return "students/form";
    }
    
    @PostMapping("/{id}")
    public String updateStudent(@PathVariable Long id,
                               @Valid @ModelAttribute Student student,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/form";
        }
        
        student.setId(id);
        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");
        return "redirect:/students";
    }
    
    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        return "redirect:/students";
    }
}