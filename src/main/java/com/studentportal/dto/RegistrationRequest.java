package com.studentportal.dto;

import com.studentportal.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record RegistrationRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank @Email String email,
        @NotBlank String fullName,
        String rollNumber,
        @NotNull Role role,
        Long departmentId,
        Long courseId,
        Integer semester
) {}
