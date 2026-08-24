package com.studentportal.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Quiz quiz;

    @ManyToOne(optional = false)
    private Student student;

    private LocalDateTime startedAt;
    private LocalDateTime submittedAt;
    private Double score = 0.0;

    public Long getId() { return id; }
    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
}
