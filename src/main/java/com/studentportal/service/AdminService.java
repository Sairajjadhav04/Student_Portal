package com.studentportal.service;

import com.studentportal.entity.Admin;
import com.studentportal.entity.Course;
import com.studentportal.entity.Department;
import com.studentportal.entity.Faculty;
import com.studentportal.entity.Student;
import com.studentportal.entity.Subject;

import com.studentportal.repository.AdminRepository;
import com.studentportal.repository.CourseRepository;
import com.studentportal.repository.DepartmentRepository;
import com.studentportal.repository.FacultyRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.SubjectRepository;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    public AdminService(
            AdminRepository adminRepository,
            StudentRepository studentRepository,
            FacultyRepository facultyRepository,
            SubjectRepository subjectRepository,
            CourseRepository courseRepository,
            DepartmentRepository departmentRepository) {

        this.adminRepository = adminRepository;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    public Admin getAdminProfile(Long adminId) {

        return adminRepository.findById(adminId)
                .orElseThrow(() ->
                        new RuntimeException("Admin not found"));
    }

    public Student addStudent(Student student) {

        return studentRepository.save(student);
    }


    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }


    public Student getStudent(Long studentId) {

        return studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));
    }


    public Student updateStudent(
            Long studentId,
            Student updatedStudent) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        student.setFullName(updatedStudent.getFullName());
        student.setEmail(updatedStudent.getEmail());
        student.setPhone(updatedStudent.getPhone());

        return studentRepository.save(student);
    }


    public String deleteStudent(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        studentRepository.delete(student);

        return "Student deleted successfully";
    }

    public Faculty addFaculty(Faculty faculty) {

        return facultyRepository.save(faculty);
    }


    public List<Faculty> getAllFaculty() {

        return facultyRepository.findAll();
    }


    public Faculty getFaculty(Long facultyId) {

        return facultyRepository.findById(facultyId)
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));
    }


    public Faculty updateFaculty(
            Long facultyId,
            Faculty updatedFaculty) {

        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));

        faculty.setFullName(updatedFaculty.getFullName());

        if (updatedFaculty.getDepartment() != null) {
            faculty.setDepartment(updatedFaculty.getDepartment());
        }

        return facultyRepository.save(faculty);
    }


    public String deleteFaculty(Long facultyId) {

        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));

        facultyRepository.delete(faculty);

        return "Faculty deleted successfully";
    }

    public Subject addSubject(Subject subject) {

        return subjectRepository.save(subject);
    }
    public List<Subject> getAllSubjects() {

        return subjectRepository.findAll();
    }
    public Subject getSubject(Long subjectId) {

        return subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));
    }

    public Subject updateSubject(
            Long subjectId,
            Subject updatedSubject) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        subject.setName(updatedSubject.getName());
        subject.setCode(updatedSubject.getCode());

        if (updatedSubject.getFaculty() != null) {
            subject.setFaculty(updatedSubject.getFaculty());
        }

        return subjectRepository.save(subject);
    }

    public String deleteSubject(Long subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        subjectRepository.delete(subject);

        return "Subject deleted successfully";
    }

    public Course addCourse(Course course) {

        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {

        return courseRepository.findAll();
    }

    public Course getCourse(Long courseId) {

        return courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));
    }
    public Course updateCourse(
            Long courseId,
            Course updatedCourse) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));
        course.setName(updatedCourse.getName());
        return courseRepository.save(course);
    }
    public String deleteCourse(Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        courseRepository.delete(course);

        return "Course deleted successfully";
    }

    public Map<String, Object> getReports() {
        Map<String, Object> reports = new HashMap<>();
        reports.put(
                "totalStudents",
                studentRepository.count()
        );
        reports.put(
                "totalFaculty",
                facultyRepository.count()
        );
        reports.put(
                "totalSubjects",
                subjectRepository.count()
        );
        reports.put(
                "totalCourses",
                courseRepository.count()
        );
        return reports;
    }
    public List<Department> getDepartments() {

        return departmentRepository.findAll();
    }
}