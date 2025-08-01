package com.studentmanagement.repository;

import com.studentmanagement.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findByStudentId(Long studentId);
    List<Mark> findByStudentIdAndExamType(Long studentId, String examType);
    
    @Query("SELECT DISTINCT m.subject FROM Mark m ORDER BY m.subject")
    List<String> findDistinctSubjects();
    
    @Query("SELECT DISTINCT m.examType FROM Mark m ORDER BY m.examType")
    List<String> findDistinctExamTypes();
    
    @Query("SELECT AVG(m.marksObtained * 100.0 / m.totalMarks) FROM Mark m WHERE m.student.id = :studentId")
    Double findAveragePercentageByStudentId(Long studentId);
}