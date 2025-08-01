package com.studentmanagement.config;

import com.studentmanagement.entity.Student;
import com.studentmanagement.entity.Mark;
import com.studentmanagement.service.StudentService;
import com.studentmanagement.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private MarkService markService;

    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("=== DataInitializer Starting - Indian Names Database ===");

            // Check if data already exists
            long existingStudents = studentService.getAllStudents().size();
            System.out.println("Existing students in database: " + existingStudents);

            if (existingStudents == 0) {
                System.out.println("Creating Indian students database...");

                // Create Indian students
                Student student1 = createStudent("Arjun", "Sharma", "arjun.sharma@email.com", 
                    "9876543210", LocalDate.of(2005, 3, 15), "123 MG Road, Mumbai", "Class 12A");
                
                Student student2 = createStudent("Priya", "Patel", "priya.patel@email.com", 
                    "9876543211", LocalDate.of(2005, 7, 22), "456 Ring Road, Delhi", "Class 12A");
                
                Student student3 = createStudent("Rahul", "Kumar", "rahul.kumar@email.com", 
                    "9876543212", LocalDate.of(2004, 11, 8), "789 Park Street, Kolkata", "Class 11B");
                
                Student student4 = createStudent("Ananya", "Singh", "ananya.singh@email.com", 
                    "9876543213", LocalDate.of(2005, 1, 12), "321 Brigade Road, Bangalore", "Class 12B");
                
                Student student5 = createStudent("Vikram", "Reddy", "vikram.reddy@email.com", 
                    "9876543214", LocalDate.of(2004, 9, 25), "654 Tank Bund Road, Hyderabad", "Class 11A");

                // Create sample marks for students
                System.out.println("\nCreating sample marks...");
                
                // Marks for Arjun Sharma
                createMark(student1, "Mathematics", "Unit Test 1", 92, 100);
                createMark(student1, "Physics", "Unit Test 1", 88, 100);
                createMark(student1, "Chemistry", "Unit Test 1", 85, 100);
                
                // Marks for Priya Patel
                createMark(student2, "Mathematics", "Unit Test 1", 95, 100);
                createMark(student2, "English", "Unit Test 1", 90, 100);
                createMark(student2, "Biology", "Unit Test 1", 87, 100);
                
                // Marks for Rahul Kumar
                createMark(student3, "Physics", "Unit Test 1", 78, 100);
                createMark(student3, "Chemistry", "Unit Test 1", 82, 100);
                
                // Marks for Ananya Singh
                createMark(student4, "English", "Unit Test 1", 93, 100);
                createMark(student4, "History", "Unit Test 1", 89, 100);
                
                // Marks for Vikram Reddy
                createMark(student5, "Mathematics", "Unit Test 1", 86, 100);

                System.out.println("\n🎉 Indian Students Database Created Successfully!");
                System.out.println("📊 Total Students: " + studentService.getAllStudents().size());
                System.out.println("📊 Total Marks: " + markService.getAllMarks().size());
                System.out.println("\n🎯 Available Students:");
                System.out.println("   1. " + student1.getFullName() + " (" + student1.getStudentClass() + ") - Mumbai");
                System.out.println("   2. " + student2.getFullName() + " (" + student2.getStudentClass() + ") - Delhi");
                System.out.println("   3. " + student3.getFullName() + " (" + student3.getStudentClass() + ") - Kolkata");
                System.out.println("   4. " + student4.getFullName() + " (" + student4.getStudentClass() + ") - Bangalore");
                System.out.println("   5. " + student5.getFullName() + " (" + student5.getStudentClass() + ") - Hyderabad");

            } else {
                System.out.println("✅ Data already exists (" + existingStudents + " students). Skipping initialization.");
            }

            System.out.println("=== DataInitializer Completed ===");

        } catch (Exception e) {
            System.err.println("❌ ERROR in DataInitializer: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    private Student createStudent(String firstName, String lastName, String email, String phone, 
                                 LocalDate dateOfBirth, String address, String studentClass) {
        try {
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(email);
            student.setPhoneNumber(phone);
            student.setDateOfBirth(dateOfBirth);
            student.setAddress(address);
            student.setStudentClass(studentClass);
            student = studentService.saveStudent(student);
            System.out.println("✅ " + student.getFullName() + " created with ID: " + student.getId());
            return student;
        } catch (Exception e) {
            System.err.println("Error creating student " + firstName + " " + lastName + ": " + e.getMessage());
            throw e;
        }
    }
    
    private void createMark(Student student, String subject, String examType, int marksObtained, int totalMarks) {
        try {
            Mark mark = new Mark();
            mark.setStudent(student);
            mark.setSubject(subject);
            mark.setExamType(examType);
            mark.setMarksObtained(marksObtained);
            mark.setTotalMarks(totalMarks);
            markService.saveMark(mark);
            System.out.println("   📝 " + student.getFullName() + " - " + subject + ": " + marksObtained + "/" + totalMarks + " (" + mark.getGrade() + ")");
        } catch (Exception e) {
            System.err.println("Error creating mark for " + student.getFullName() + ": " + e.getMessage());
        }
    }
}