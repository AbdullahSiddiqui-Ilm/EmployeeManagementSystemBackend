package com.example.part1.service;

import com.example.part1.exception.ResourceNotFoundException;
import com.example.part1.model.Employee;
import com.example.part1.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(String department) {
        if (department == null || department.isBlank()) { return employeeRepository.findAll();}
        return employeeRepository.findByAssignmentsDepartmentNameIgnoreCase(department);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setName(updatedEmployee.getName());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setAddress(updatedEmployee.getAddress());
            employee.setContractType(updatedEmployee.getContractType());
            employee.setStartDate(updatedEmployee.getStartDate());
            employee.setSalary(updatedEmployee.getSalary());
            return employeeRepository.save(employee);
        });
    }

    public boolean deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            return false;
        }
        employeeRepository.deleteById(id);
        return true;
    }

    public Optional<Employee> promoteEmployee(Long id) {
        return employeeRepository.findById(id).map(employee -> {
            BigDecimal currentSalary = employee.getSalary();
            LocalDate startDate = employee.getStartDate();
            LocalDate sixMonthsBefore = LocalDate.now().minusMonths(6);
            if (currentSalary == null || startDate == null ||
                    startDate.isAfter(sixMonthsBefore)) {
                throw new IllegalArgumentException("Employee is not eligible for a promotion");
            }
            employee.setSalary(currentSalary.multiply(new BigDecimal("1.10")));
            if (employee.getRank() == Employee.Rank.RANK1) {
                employee.setRank(Employee.Rank.RANK2);
            }
            return employeeRepository.save(employee);
        });
    }
}