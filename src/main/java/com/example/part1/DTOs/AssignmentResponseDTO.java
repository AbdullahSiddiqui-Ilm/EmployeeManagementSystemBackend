package com.example.part1.DTOs;

import com.example.part1.model.Assignment;

public class AssignmentResponseDTO {
    private Long employeeId;

    private Long departmentId;

    private String role;

    private Integer accessLevel;


    public AssignmentResponseDTO(Assignment assignment) {

    }

    public Long getEmployeeId() {

        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {

        this.employeeId = employeeId;
    }

    public Long getDepartmentId() {

        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {

        this.departmentId = departmentId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }
}
