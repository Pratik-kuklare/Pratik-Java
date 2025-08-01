package com.studentmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;
    
    @Email(message = "Please provide a valid email")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @NotNull(message = "Date of birth is required")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "Class is required")
    @Column(name = "student_class")
    private String studentClass;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mark> marks;
    
    // Constructors
    public Student() {}
    
    public Student(String firstName, String lastName, String email, String phoneNumber, 
                  LocalDate dateOfBirth, String address, String studentClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.studentClass = studentClass;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }
    
    public List<Mark> getMarks() { return marks; }
    public void setMarks(List<Mark> marks) { this.marks = marks; }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}