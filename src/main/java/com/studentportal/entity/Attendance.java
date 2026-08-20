package com.studentportal.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "subject_id", "attendanceDate"}))
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Student student;
    @ManyToOne(optional = false)
    private Subject subject;
    private LocalDate attendanceDate;
    private boolean present;
    public Long getId()
    {
        return id;
    }
    public Student getStudent()
    {
        return student;
    }
    public void setStudent(Student student)
    {
        this.student = student;
    }
    public Subject getSubject()
    {
        return subject;
    }
    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }
    public LocalDate getAttendanceDate()
    {
        return attendanceDate;
    }
    public void setAttendanceDate(LocalDate attendanceDate)
    {
        this.attendanceDate = attendanceDate;
    }
    public boolean isPresent()
    {
        return present;
    }
    public void setPresent(boolean present)
    {
        this.present = present;
    }
}
