package com.shabnam.employee_management.Service;

import com.shabnam.employee_management.model.Employee;
import com.shabnam.employee_management.model.user;
import com.shabnam.employee_management.Repository.EmployeeRepository;
import com.shabnam.employee_management.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String lastGeneratedPassword;

    public String getLastGeneratedPassword() {
        return lastGeneratedPassword;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Employee createEmployee(Employee employee) {
        Employee saved = employeeRepository.save(employee);

        String username = employee.getEmail();
        String rawPassword = generatePassword(employee.getPhone());

        // store for controller
        this.lastGeneratedPassword = rawPassword;

        if (userRepository.findByUsername(username).isEmpty()) {
            user user = new user();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole("EMPLOYEE");
            userRepository.save(user);
        }

        return saved;
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = getEmployeeById(id);
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPhone(employeeDetails.getPhone());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setDesignation(employeeDetails.getDesignation());
        employee.setSalary(employeeDetails.getSalary());
        employee.setJoiningDate(employeeDetails.getJoiningDate());
        employee.setStatus(employeeDetails.getStatus());
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        userRepository.findByUsername(employee.getEmail())
                .ifPresent(userRepository::delete);
        employeeRepository.delete(employee);
    }

    public List<Employee> getByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    public List<Employee> searchByName(String name) {
        return employeeRepository.findByFirstNameContainingIgnoreCase(name);
    }

    private String generatePassword(String phone) {
        if (phone != null && phone.length() >= 4) {
            return "emp@" + phone.substring(phone.length() - 4);
        }
        return "emp@1234";
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found!"));
    }

    public void changePassword(String username, String currentPassword, String newPassword) {
        user user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}