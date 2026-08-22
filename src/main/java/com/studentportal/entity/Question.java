package com.studentportal.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2000, nullable = false)
    private String questionText;
    private Double marks = 1.0;
    @ManyToOne(optional = false)
    private Quiz quiz;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();
    public Long getId() { return id; }
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public Double getMarks() { return marks; }
    public void setMarks(Double marks) { this.marks = marks; }
    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }
    public List<Option> getOptions() { return options; }
}
