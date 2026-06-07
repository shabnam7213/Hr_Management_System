package com.shabnam.employee_management.Controller;

import com.shabnam.employee_management.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                authService.register(
                        body.get("username"),
                        body.get("password"),
                        body.get("role")
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                authService.login(body.get("username"), body.get("password"))
        );
    }
}