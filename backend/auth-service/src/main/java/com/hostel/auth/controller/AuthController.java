package com.hostel.auth.controller;

import com.hostel.auth.dto.*;
import com.hostel.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/user")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/register/employee")
    public ResponseEntity<RegisterResponse> registerEmployee(@Valid @RequestBody RegisterEmployeeRequest request) {
        return ResponseEntity.ok(authService.registerEmployee(request));
    }

    @PostMapping("/register/owner")
    public ResponseEntity<RegisterResponse> registerOwner(@Valid @RequestBody RegisterOwnerRequest request) {
        return ResponseEntity.ok(authService.registerOwner(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
