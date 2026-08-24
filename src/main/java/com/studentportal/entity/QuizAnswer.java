package com.studentportal.entity;
import jakarta.persistence.*;
@Entity
public class QuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private QuizAttempt attempt;
    @ManyToOne(optional = false)
    private Question question;
    @ManyToOne
    private Option selectedOption;
    public Long getId() { return id; }
    public QuizAttempt getAttempt() { return attempt; }
    public void setAttempt(QuizAttempt attempt) { this.attempt = attempt; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
    public Option getSelectedOption() { return selectedOption; }
    public void setSelectedOption(Option selectedOption) { this.selectedOption = selectedOption; }
}
