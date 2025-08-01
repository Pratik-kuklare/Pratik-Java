package com.studentmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Subject is required")
    private String subject;
    
    @NotNull(message = "Marks obtained is required")
    @Min(value = 0, message = "Marks cannot be negative")
    @Max(value = 100, message = "Marks cannot exceed 100")
    @Column(name = "marks_obtained")
    private Integer marksObtained;
    
    @NotNull(message = "Total marks is required")
    @Min(value = 1, message = "Total marks must be at least 1")
    @Column(name = "total_marks")
    private Integer totalMarks;
    
    @NotBlank(message = "Exam type is required")
    @Column(name = "exam_type")
    private String examType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    // Constructors
    public Mark() {}
    
    public Mark(String subject, Integer marksObtained, Integer totalMarks, String examType, Student student) {
        this.subject = subject;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
        this.examType = examType;
        this.student = student;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public Integer getMarksObtained() { return marksObtained; }
    public void setMarksObtained(Integer marksObtained) { this.marksObtained = marksObtained; }
    
    public Integer getTotalMarks() { return totalMarks; }
    public void setTotalMarks(Integer totalMarks) { this.totalMarks = totalMarks; }
    
    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }
    
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    
    public Double getPercentage() {
        if (totalMarks != null && totalMarks > 0) {
            return (marksObtained.doubleValue() / totalMarks.doubleValue()) * 100;
        }
        return 0.0;
    }
    
    public String getGrade() {
        double percentage = getPercentage();
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C";
        else if (percentage >= 50) return "D";
        else return "F";
    }
}