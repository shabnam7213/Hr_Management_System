package com.shabnam.employee_management.dto;

import com.shabnam.employee_management.model.Employee;
import lombok.Data;

@Data
public class EmployeeResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private Double salary;
    private String joiningDate;
    private String status;
    private String generatedPassword;

    public static EmployeeResponse from(Employee emp, String password) {
        EmployeeResponse res = new EmployeeResponse();
        res.setId(emp.getId());
        res.setFirstName(emp.getFirstName());
        res.setLastName(emp.getLastName());
        res.setEmail(emp.getEmail());
        res.setPhone(emp.getPhone());
        res.setDepartment(emp.getDepartment());
        res.setDesignation(emp.getDesignation());
        res.setSalary(emp.getSalary());
        res.setJoiningDate(emp.getJoiningDate() != null ? emp.getJoiningDate().toString() : null);
        res.setStatus(emp.getStatus());
        res.setGeneratedPassword(password);
        return res;
    }
}