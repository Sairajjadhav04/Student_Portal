package com.studentportal.service;

import com.studentportal.entity.*;
import com.studentportal.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    public AdminService(StudentRepository studentRepository, FacultyRepository facultyRepository,
                        DepartmentRepository departmentRepository, CourseRepository courseRepository,
                        SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Student> students() { return studentRepository.findAll(); }
    public List<Faculty> faculty() { return facultyRepository.findAll(); }
    public List<Department> departments() { return departmentRepository.findAll(); }
    public List<Course> courses() { return courseRepository.findAll(); }
    public List<Subject> subjects() { return subjectRepository.findAll(); }

    public Department saveDepartment(Department d) { return departmentRepository.save(d); }
    public Course saveCourse(Course c) { return courseRepository.save(c); }
    public Subject saveSubject(Subject s) { return subjectRepository.save(s); }
}
