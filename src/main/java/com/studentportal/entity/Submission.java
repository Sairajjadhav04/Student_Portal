package com.studentportal.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Assignment assignment;
    @ManyToOne(optional = false)
    private Student student;
    private String filePath;
    private LocalDateTime submittedAt;
    private Double marks;
    public Long getId() { return id; }
    public Assignment getAssignment() { return assignment; }
    public void setAssignment(Assignment assignment) { this.assignment = assignment; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public Double getMarks() { return marks; }
    public void setMarks(Double marks) { this.marks = marks; }
}
