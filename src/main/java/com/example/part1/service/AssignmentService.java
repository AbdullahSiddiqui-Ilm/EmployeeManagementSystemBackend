package com.example.part1.service;

import com.example.part1.DTOs.AssignmentRequestDTO;
import com.example.part1.DTOs.AssignmentResponseDTO;
import com.example.part1.model.Assignment;
import com.example.part1.model.Department;
import com.example.part1.model.Employee;
import com.example.part1.repository.AssignmentRepository;
import com.example.part1.repository.DepartmentRepository;
import com.example.part1.repository.EmployeeRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository){
        this.assignmentRepository = assignmentRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Assignment> getAllAssignments(String employee){
        return assignmentRepository.findAll();
    }

    public Optional<Assignment> getAssignmentByID(Long id){
        return assignmentRepository.findById(id);

    }
    public AssignmentResponseDTO createAssignment(AssignmentRequestDTO requestDTO) throws BadRequestException{
        Optional<Employee> employee = employeeRepository.findById(requestDTO.getEmployeeId());
        Optional<Department> department = departmentRepository.findById(requestDTO.getDepartmentId());

        if (employee.isEmpty() || department.isEmpty()) {
            throw new BadRequestException("Employee ID or Department ID is invalid, please try again.");
        }
        Assignment assignment = new Assignment();
        assignment.setEmployee(employee.get());
        assignment.setDepartment(department.get());
        assignment.setAccessLevel(requestDTO.getAccessLevel());
        assignment.setRole(requestDTO.getRole());

        Assignment saved = assignmentRepository.save(assignment);
        return new AssignmentResponseDTO(saved);
    }
}
