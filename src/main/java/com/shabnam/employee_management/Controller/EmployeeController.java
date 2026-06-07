package com.shabnam.employee_management.Controller;

import com.shabnam.employee_management.dto.EmployeeResponse;
import com.shabnam.employee_management.model.Employee;
import com.shabnam.employee_management.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee);
        String password = employeeService.getLastGeneratedPassword();
        return ResponseEntity.ok(EmployeeResponse.from(saved, password));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/department/{dept}")
    public List<Employee> getByDepartment(@PathVariable String dept) {
        return employeeService.getByDepartment(dept);
    }

    @GetMapping("/search")
    public List<Employee> searchByName(@RequestParam String name) {
        return employeeService.searchByName(name);
    }


    // Get logged in employee profile
    @GetMapping("/profile")
    public ResponseEntity<Employee> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        Employee employee = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(employee);
    }

    // Change password
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        String username = authentication.getName();
        employeeService.changePassword(username, body.get("currentPassword"), body.get("newPassword"));
        return ResponseEntity.ok("Password changed successfully!");
    }
}
