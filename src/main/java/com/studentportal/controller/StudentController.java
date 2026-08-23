package com.studentportal.controller;
import com.studentportal.entity.Student;
import com.studentportal.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('STUDENT')")
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }
    @GetMapping("/dashboard")
    public Object dashboard(Authentication auth) {
        return service.dashboard(auth.getName());
    }
    @GetMapping("/profile")
    public Student profile(Authentication auth) {
        return service.getByUsername(auth.getName());
    }
    @PutMapping("/profile")
    public Student updateProfile(Authentication auth, @RequestBody Student student) {
        return service.updateProfile(auth.getName(), student);
    }
    @GetMapping("/all")
    public Object allStudents() {
        return service.getAll();
    }
}
