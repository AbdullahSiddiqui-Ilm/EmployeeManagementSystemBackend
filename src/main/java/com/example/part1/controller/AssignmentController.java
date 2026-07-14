package com.example.part1.controller;

import com.example.part1.DTOs.AssignmentRequestDTO;
import com.example.part1.DTOs.AssignmentResponseDTO;
import com.example.part1.exception.ResourceNotFoundException;
import com.example.part1.model.Assignment;
import com.example.part1.model.Department;
import com.example.part1.model.Employee;
import com.example.part1.repository.AssignmentRepository;
import com.example.part1.repository.DepartmentRepository;
import com.example.part1.repository.EmployeeRepository;
import com.example.part1.service.AssignmentService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentRepository assignmentRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, AssignmentService assignmentService) {
        this.assignmentRepository = assignmentRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public List<Assignment> getAllAssignments(@RequestParam(required = false) String employee) {
        return assignmentService.getAllAssignments(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        Assignment assignment = assignmentService.getAssignmentByID(id).orElseThrow(() -> new ResourceNotFoundException("Assignment was not found"));
        return ResponseEntity.ok(assignment);
    }

    @PostMapping
    public ResponseEntity<AssignmentResponseDTO> createAssignment(@RequestBody AssignmentRequestDTO requestDTO) throws BadRequestException {
        AssignmentResponseDTO assignment = assignmentService.createAssignment(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        if (!assignmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        assignmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
