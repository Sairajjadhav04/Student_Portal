package com.studentportal.controller;

import com.studentportal.dto.QuizDTO;
import com.studentportal.entity.*;
import com.studentportal.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    // STUDENT
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/available")
    public Object available() {
        return service.publishedQuizzes();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/student/{quizId}/start")
    public QuizAttempt start(@PathVariable Long quizId, Authentication auth) {
        return service.startAttempt(quizId, auth.getName());
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/student/attempt/{attemptId}/submit")
    public QuizAttempt submit(@PathVariable Long attemptId,
                              Authentication auth,
                              @RequestBody Map<Long, Long> answers) {
        return service.submit(attemptId, auth.getName(), answers);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/my-attempts")
    public Object myAttempts(Authentication auth) {
        return service.myAttempts(auth.getName());
    }

    // FACULTY
    @PreAuthorize("hasRole('FACULTY')")
    @PostMapping("/faculty/create")
    public Quiz create(@Valid @RequestBody QuizDTO dto, Authentication auth) {
        return service.createQuiz(auth.getName(), dto);
    }

    @PreAuthorize("hasRole('FACULTY')")
    @GetMapping("/faculty/my-quizzes")
    public Object facultyQuizzes(Authentication auth) {
        return service.facultyQuizzes(auth.getName());
    }

    @PreAuthorize("hasRole('FACULTY')")
    @PutMapping("/faculty/{id}/publish")
    public Quiz publish(@PathVariable Long id, Authentication auth) {
        return service.publish(id, auth.getName());
    }

    @PreAuthorize("hasRole('FACULTY')")
    @PutMapping("/faculty/{id}/close")
    public Quiz close(@PathVariable Long id, Authentication auth) {
        return service.close(id, auth.getName());
    }

    @PreAuthorize("hasRole('FACULTY')")
    @GetMapping("/faculty/{id}/reports")
    public Object reports(@PathVariable Long id, Authentication auth) {
        return service.quizReports(id, auth.getName());
    }

    // ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public Object all() {
        return service.allQuizzes();
    }
}
