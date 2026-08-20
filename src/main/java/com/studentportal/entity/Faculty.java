package com.studentportal.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "Faculty")
public class Faculty extends User {
    private String fullName;
    @ManyToOne
    private Department department;
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
}