package com.example.part1.DTOs;

public class AssignmentRequestDTO {

    private Long employeeId;

    private Long departmentId;


    public AssignmentRequestDTO() {

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

}


