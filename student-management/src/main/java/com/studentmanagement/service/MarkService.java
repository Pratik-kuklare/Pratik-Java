package com.studentmanagement.service;

import com.studentmanagement.entity.Mark;
import com.studentmanagement.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MarkService {
    
    @Autowired
    private MarkRepository markRepository;
    
    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }
    
    public Optional<Mark> getMarkById(Long id) {
        return markRepository.findById(id);
    }
    
    public Mark saveMark(Mark mark) {
        return markRepository.save(mark);
    }
    
    public void deleteMark(Long id) {
        markRepository.deleteById(id);
    }
    
    public List<Mark> getMarksByStudentId(Long studentId) {
        return markRepository.findByStudentId(studentId);
    }
    
    public List<Mark> getMarksByStudentIdAndExamType(Long studentId, String examType) {
        return markRepository.findByStudentIdAndExamType(studentId, examType);
    }
    
    public List<String> getAllSubjects() {
        return markRepository.findDistinctSubjects();
    }
    
    public List<String> getAllExamTypes() {
        return markRepository.findDistinctExamTypes();
    }
    
    public Double getAveragePercentageByStudentId(Long studentId) {
        return markRepository.findAveragePercentageByStudentId(studentId);
    }
}