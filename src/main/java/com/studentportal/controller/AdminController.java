package com.studentportal.controller;
import com.studentportal.entity.*;
import com.studentportal.service.AdminService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService service;
    public AdminController(AdminService service) {
        this.service = service;
    }
    @GetMapping("/students")
    public Object students() { return service.students(); }
    @GetMapping("/faculty")
    public Object faculty() { return service.faculty(); }
    @GetMapping("/departments")
    public Object departments() { return service.departments(); }
    @GetMapping("/courses")
    public Object courses() { return service.courses(); }
    @GetMapping("/subjects")
    public Object subjects() { return service.subjects(); }
    @PostMapping("/departments")
    public Department department(@RequestBody Department d) { return service.saveDepartment(d); }
    @PostMapping("/courses")
    public Course course(@RequestBody Course c) { return service.saveCourse(c); }
    @PostMapping("/subjects")
    public Subject subject(@RequestBody Subject s) { return service.saveSubject(s); }
}
