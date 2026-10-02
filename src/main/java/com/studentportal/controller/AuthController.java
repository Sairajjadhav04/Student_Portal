package com.studentportal.controller;

import com.studentportal.dto.LoginRequest;
import com.studentportal.dto.RegistrationRequest;
import com.studentportal.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }


    // =========================================
    // REGISTER
    // =========================================

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegistrationRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of(
                                "message",
                                authService.register(request)
                        )
                );
    }


    // =========================================
    // LOGIN
    // =========================================

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {


        String token =
                authService.login(request);


        String role =
                authService.getRole(
                        request.username()
                );


        return ResponseEntity.ok(

                Map.of(
                        "token", token,
                        "role", role
                )
        );
    }
}