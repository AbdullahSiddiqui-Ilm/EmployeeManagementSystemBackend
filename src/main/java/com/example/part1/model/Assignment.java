package com.example.part1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    private Integer accessLevel;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference("employee-assignment")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference("department-assignment")
    private Department department;

    public Assignment() {
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
