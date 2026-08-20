package com.studentportal.entity;
import jakarta.persistence.*;
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    private Integer credits;
    @ManyToOne
    private Course course;
    @ManyToOne
    private Faculty faculty;
    public Long getId()
    {
        return id;
    }
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public Integer getCredits()
    {
        return credits;
    }
    public void setCredits(Integer credits)
    {
        this.credits = credits;
    }
    public Course getCourse()
    {
        return course;
    }
    public void setCourse(Course course)
    {
        this.course = course;
    }
    public Faculty getFaculty()
    {
        return faculty;
    }
    public void setFaculty(Faculty faculty)
    {
        this.faculty = faculty;
    }
}
