package com.example.part1.service;

import com.example.part1.model.Assignment;
import com.example.part1.repository.AssignmentRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository){this.assignmentRepository = assignmentRepository;}

    public List<Assignment> getAllAssignments(String employee){
        return assignmentRepository.findAll();
    }

    public Optional<Assignment> getAssignmentByID(Long id){
        return assignmentRepository.findById(id);

    }
}
