package com.shabnam.employee_management.Service;

import com.shabnam.employee_management.model.user;
import com.shabnam.employee_management.Repository.UserRepository;
import com.shabnam.employee_management.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(String username, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }
        user user = new user();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role != null ? role : "ADMIN");
        userRepository.save(user);
        return "User registered successfully!";
    }

    public Map<String, String> login(String username, String password) {
        user user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }
        String token = jwtUtil.generateToken(username, user.getRole());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole());
        response.put("username", username);
        return response;
    }
}