package com.studentportal.controller;
import com.studentportal.entity.*;
import com.studentportal.service.AdminService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.Map;
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/profile/{adminId}")
    public Admin getAdminProfile(@PathVariable Long adminId) {
        return adminService.getAdminProfile(adminId);
    }
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return adminService.addStudent(student);
    }
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return adminService.getAllStudents();
    }
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable Long studentId) {
        return adminService.getStudent(studentId);
    }
    @PutMapping("/students/{studentId}")
    public Student updateStudent(
            @PathVariable Long studentId,
            @RequestBody Student student) {

        return adminService.updateStudent(studentId, student);
    }
    @DeleteMapping("/students/{studentId}")
    public String deleteStudent(@PathVariable Long studentId) {
        return adminService.deleteStudent(studentId);
    }

    @PostMapping("/faculty")
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return adminService.addFaculty(faculty);
    }
    @GetMapping("/faculty")
    public List<Faculty> getAllFaculty() {
        return adminService.getAllFaculty();
    }
    @GetMapping("/faculty/{facultyId}")
    public Faculty getFaculty(@PathVariable Long facultyId) {
        return adminService.getFaculty(facultyId);
    }
    @PutMapping("/faculty/{facultyId}")
    public Faculty updateFaculty(
            @PathVariable Long facultyId,
            @RequestBody Faculty faculty) {

        return adminService.updateFaculty(facultyId, faculty);
    }
    @DeleteMapping("/faculty/{facultyId}")
    public String deleteFaculty(@PathVariable Long facultyId) {
        return adminService.deleteFaculty(facultyId);
    }
    @PostMapping("/subjects")
    public Subject addSubject(@RequestBody Subject subject) {
        return adminService.addSubject(subject);
    }
    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return adminService.getAllSubjects();
    }
    @GetMapping("/subjects/{subjectId}")
    public Subject getSubject(@PathVariable Long subjectId) {
        return adminService.getSubject(subjectId);
    }
    @PutMapping("/subjects/{subjectId}")
    public Subject updateSubject(
            @PathVariable Long subjectId,
            @RequestBody Subject subject) {
        return adminService.updateSubject(subjectId, subject);
    }
    @DeleteMapping("/subjects/{subjectId}")
    public String deleteSubject(@PathVariable Long subjectId) {
        return adminService.deleteSubject(subjectId);
    }
    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course) {
        return adminService.addCourse(course);
    }
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return adminService.getAllCourses();
    }

    @GetMapping("/courses/{courseId}")
    public Course getCourse(@PathVariable Long courseId) {
        return adminService.getCourse(courseId);
    }
    @PutMapping("/courses/{courseId}")
    public Course updateCourse(
            @PathVariable Long courseId,
            @RequestBody Course course) {

        return adminService.updateCourse(courseId, course);
    }
    @DeleteMapping("/courses/{courseId}")
    public String deleteCourse(@PathVariable Long courseId) {
        return adminService.deleteCourse(courseId);
    }
    @GetMapping("/reports")
    public Map<String, Object> getReports() {
        return adminService.getReports();
    }
    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return adminService.getDepartments();
    }
}