package com.example.part1.controller;

import com.example.part1.exception.ResourceNotFoundException;
import com.example.part1.model.Employee;
import com.example.part1.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam (required = false) String department) {
        return employeeService.getAllEmployees(department);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return ResponseEntity.ok(employee);
    }


    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return ResponseEntity.ok(employee);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id);

        if (!deleted) {
            throw new ResourceNotFoundException("Employee not found");
        }

        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/promote")
    public ResponseEntity<Employee> promoteEmployee(@PathVariable Long id) {
        Employee employee = employeeService.promoteEmployee(id).orElseThrow(() -> new ResourceNotFoundException("Employeee not found"));
        return ResponseEntity.ok(employee);
    }
}