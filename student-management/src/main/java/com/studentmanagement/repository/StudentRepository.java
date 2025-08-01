package com.studentmanagement.repository;

import com.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    List<Student> findByStudentClass(String studentClass);
    boolean existsByEmail(String email);
    
    @Query("SELECT DISTINCT s.studentClass FROM Student s ORDER BY s.studentClass")
    List<String> findDistinctClasses();
}