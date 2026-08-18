package com.studentportal.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "students")
public class Student extends User {
    @Column(unique = true)
    private String rollNumber;
    private String fullName;
    private String phone;
    @ManyToOne
    private Department department;
    @ManyToOne
    private Course course;
    private Integer semester;
    public String getRollNumber()
    {
        return rollNumber;
    }
    public void setRollNumber(String rollNumber)
    {
        this.rollNumber = rollNumber;
    }
    public String getFullName()
    {
        return fullName;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public Department getDepartment()
    {
        return department;
    }
    public void setDepartment(Department department)
    {
        this.department = department;
    }
    public Course getCourse()
    {
        return course;
    }
    public void setCourse(Course course)
    {
        this.course = course;
    }
    public Integer getSemester()
    {
        return semester;
    }
    public void setSemester(Integer semester)
    {
        this.semester = semester;
    }
}
