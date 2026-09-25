package com.studentportal.controller;

import com.studentportal.dto.QuizDTO;
import com.studentportal.entity.Quiz;
import com.studentportal.entity.QuizAttempt;
import com.studentportal.service.QuizService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/available")
    public List<Quiz> getAvailableQuizzes() {
        return quizService.publishedQuizzes();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{quizId}")
    public Quiz getQuiz(@PathVariable Long quizId) {
        return quizService.getQuiz(quizId);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/student/{quizId}/start")
    public QuizAttempt startQuiz(
            @PathVariable Long quizId,
            Authentication authentication) {

        return quizService.startAttempt(
                quizId,
                authentication.getName()
        );
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/student/attempt/{attemptId}/submit")
    public QuizAttempt submitQuiz(
            @PathVariable Long attemptId,
            @RequestBody Map<Long, Long> answers,
            Authentication authentication) {

        return quizService.submit(
                attemptId,
                authentication.getName(),
                answers
        );
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/my-attempts")
    public List<QuizAttempt> getMyAttempts(
            Authentication authentication) {

        return quizService.myAttempts(
                authentication.getName()
        );
    }

    @PreAuthorize("hasRole('FACULTY')")
    @PostMapping("/faculty/create")
    public Quiz createQuiz(
            @Valid @RequestBody QuizDTO quizDTO,
            Authentication authentication) {

        return quizService.createQuiz(
                authentication.getName(),
                quizDTO
        );
    }

    @PreAuthorize("hasRole('FACULTY')")
    @GetMapping("/faculty/my-quizzes")
    public List<Quiz> getFacultyQuizzes(
            Authentication authentication) {

        return quizService.facultyQuizzes(
                authentication.getName()
        );
    }

    @PreAuthorize("hasRole('FACULTY')")
    @PutMapping("/faculty/{quizId}/publish")
    public Quiz publishQuiz(
            @PathVariable Long quizId,
            Authentication authentication) {

        return quizService.publish(
                quizId,
                authentication.getName()
        );
    }

    @PreAuthorize("hasRole('FACULTY')")
    @PutMapping("/faculty/{quizId}/close")
    public Quiz closeQuiz(
            @PathVariable Long quizId,
            Authentication authentication) {

        return quizService.close(
                quizId,
                authentication.getName()
        );
    }

    @PreAuthorize("hasRole('FACULTY')")
    @GetMapping("/faculty/{quizId}/reports")
    public List<QuizAttempt> getQuizReports(
            @PathVariable Long quizId,
            Authentication authentication) {

        return quizService.quizReports(
                quizId,
                authentication.getName()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public List<Quiz> getAllQuizzes() {
        return quizService.allQuizzes();
    }
}