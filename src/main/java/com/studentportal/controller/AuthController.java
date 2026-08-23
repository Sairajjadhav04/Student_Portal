package com.studentportal.controller;
import com.studentportal.dto.*;
import com.studentportal.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", authService.register(request)));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(Map.of("token", authService.login(request)));
    }
}