package com.studentportal.controller;
import com.studentportal.repository.SubjectRepository;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectRepository repository;
    public SubjectController(SubjectRepository repository) {
        this.repository = repository;
    }
    @GetMapping
    public Object all() {
        return repository.findAll();
    }
    @GetMapping("/course/{courseId}")
    public Object byCourse(@PathVariable Long courseId) {
        return repository.findByCourseId(courseId);
    }
}
