package com.example.part1.controller;

import com.example.part1.DTOs.AssignmentRequestDTO;
import com.example.part1.model.Assignment;
import com.example.part1.model.Department;
import com.example.part1.model.Employee;
import com.example.part1.repository.AssignmentRepository;
import com.example.part1.repository.DepartmentRepository;
import com.example.part1.repository.EmployeeRepository;
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

    public AssignmentController(AssignmentRepository assignmentRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.assignmentRepository = assignmentRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        return assignment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody AssignmentRequestDTO requestDTO) throws BadRequestException {
        Optional<Employee> employee = employeeRepository.findById(requestDTO.getEmployeeId());
        Optional<Department> department = departmentRepository.findById(requestDTO.getDepartmentId());

        if (employee.isEmpty() || department.isEmpty()) {
            throw new BadRequestException("Employee ID or Department ID is invalid, please try again.");
        }
        Assignment assignment = new Assignment();
        assignment.setEmployee(employee.get());
        assignment.setDepartment(department.get());

        Assignment savedAssignment = assignmentRepository.save(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignment);
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
