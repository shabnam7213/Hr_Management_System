package com.shabnam.employee_management.Repository;

import com.shabnam.employee_management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(String department);

    List<Employee> findByFirstNameContainingIgnoreCase(String name);
    Optional<Employee> findByEmail(String email);

}