package com.studentportal.controller;
import com.studentportal.entity.*;
import com.studentportal.service.FacultyService;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@PreAuthorize("hasRole('FACULTY')")
@RequestMapping("/api/faculty")
public class FacultyController {
    private final FacultyService service;
    public FacultyController(FacultyService service) {
        this.service = service;
    }
    @GetMapping("/profile")
    public Faculty profile(Authentication auth) {
        return service.getByUsername(auth.getName());
    }
    @GetMapping("/students")
    public Object students() {
        return service.students();
    }
    @GetMapping("/subjects")
    public Object subjects(Authentication auth) {
        return service.subjects(auth.getName());
    }
    @PostMapping("/assignments")
    public Assignment createAssignment(Authentication auth, @RequestBody Assignment assignment) {
        return service.createAssignment(auth.getName(), assignment);
    }
    @PostMapping("/attendance")
    public Attendance attendance(@RequestBody Attendance attendance) {
        return service.saveAttendance(attendance);
    }
    @PostMapping("/marks")
    public Marks marks(@RequestBody Marks marks) {
        return service.saveMarks(marks);
    }
}
