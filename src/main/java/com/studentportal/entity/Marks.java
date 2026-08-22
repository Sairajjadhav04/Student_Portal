package com.studentportal.entity;
import jakarta.persistence.*;
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "subject_id", "examType"}))
public class Marks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne(optional = false)
    private Student student;
    @ManyToOne(optional = false)
    private Subject subject;

    private String examType;
    private double obtainedMarks;
    private double totalMarks;

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public double getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(double obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }
}