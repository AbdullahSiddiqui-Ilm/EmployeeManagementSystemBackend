package com.example.part1.service;

import com.example.part1.model.Employee;
import com.example.part1.repository.EmployeeRepository;
import org.hibernate.dialect.temptable.TemporaryTableKind;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void searchEmployeeId_returnsFoundEmployee() {

        Employee employee = new Employee();
        employee.setId(123L);
        employee.setName("John");

        when(employeeRepository.findById(123L)).thenReturn(Optional.of(employee));
        Optional<Employee> result = employeeService.getEmployeeById(123L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }

    @Test
    void deleteEmployee_returnsTrueWhen_EmployeeObjectFound(){
        when(employeeRepository.existsById(123L)).thenReturn(true);

        boolean result = employeeService.deleteEmployee(123L);
        assertTrue(result);
        verify(employeeRepository).deleteById(123L);
    }
    @Test
    void promoteEmployeeReturnsEmptyWhenEmployeeNotFound(){
        when(employeeRepository.findById(55L)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.promoteEmployee(55L);
        assertTrue(result.isEmpty());
        verify(employeeRepository, never()).save(any(Employee.class));
    }
    @Test
    void saveEmployeeReturnsA_SavedEmployee(){
        Employee employee = new Employee();
        employee.setName("John");
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee result = employeeService.saveEmployee(employee);
        assertEquals("John", result.getName());
        verify(employeeRepository).save(employee);
    }
    @Test
    void updateEmployeereturnsEmpty_whenEmployeeNotFound() {
        Employee updated = new Employee();
        updated.setName("New Name");

        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.updateEmployee(99L, updated);

        assertTrue(result.isEmpty());
        verify(employeeRepository, never()).save(any(Employee.class));
    }
    @Test
    void promoteEmployeereturnsPromotedEmployee_whenEligible() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setSalary(new BigDecimal("1000"));
        employee.setStartDate(LocalDate.now().minusMonths(7));
        employee.setRank(Employee.Rank.RANK1);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        Optional<Employee> result = employeeService.promoteEmployee(1L);
        assertTrue(result.isPresent());
        assertEquals(Employee.Rank.RANK2, result.get().getRank());
        verify(employeeRepository).save(employee);
    }
    @Test
    void getEmployeeByIdreturnsEmpty_whenEmployeeNotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.getEmployeeById(99L);
        assertTrue(result.isEmpty());
    }
    @Test
    void deleteEmployeereturnsFalse_whenEmployeeNotFound() {
        when(employeeRepository.existsById(99L)).thenReturn(false);

        boolean result = employeeService.deleteEmployee(99L);
        assertFalse(result);
        verify(employeeRepository, never()).deleteById(anyLong());
    }
    @Test
    void promoteEmployeethrowsAnException_whenEmployeeNotEligible() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setSalary(new BigDecimal("15000"));
        employee.setStartDate(LocalDate.now().minusMonths(2));
        employee.setRank(Employee.Rank.RANK1);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> employeeService.promoteEmployee(1L)
        );
        assertEquals("Employee is not eligible for a promotion", exception.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class));
    }
    @Test
    void updateEmployee_returnsUpdatedEmployee_whenEmployeeExists() {
        Employee existing = new Employee();
        existing.setId(1L);
        existing.setName("Jon");

        Employee updated = new Employee();
        updated.setName("John");
        updated.setEmail("john@yahoo.co.uk");
        updated.setAddress("5 park road");
        updated.setContractType("Permanent");
        updated.setStartDate(LocalDate.of(2024, 1, 1));
        updated.setSalary(new BigDecimal("7000"));

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(existing)).thenReturn(existing);
        Optional<Employee> result = employeeService.updateEmployee(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        assertEquals("john@yahoo.co.uk", result.get().getEmail());
        assertEquals("5 park road", result.get().getAddress());
    }

}