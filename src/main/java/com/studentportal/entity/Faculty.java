package com.studentportal.entity;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "Faculty")
public class Faculty extends User {
    private String fullName;
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "faculty")
    private List<Subject> subjects;
    public String getFullName()
    {
        return fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    public Department getDepartment()
    {
        return department;
    }
    public void setDepartment(Department department)
    {
        this.department = department;
    }
    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}