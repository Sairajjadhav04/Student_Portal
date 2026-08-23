package com.studentportal.controller;
import com.studentportal.repository.*;
import com.studentportal.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/student/academics")
@PreAuthorize("hasRole('STUDENT')")
public class StudentAcademicController {
    private final StudentService studentService;
    private final AttendenceRepository attendanceRepository;
    private final MarksRepository marksRepository;
    private final ResultRepository resultRepository;
    private final AssignmentRepository assignmentRepository;
    public StudentAcademicController(StudentService studentService,
                                     AttendenceRepository attendanceRepository,
                                     MarksRepository marksRepository,
                                     ResultRepository resultRepository,
                                     AssignmentRepository assignmentRepository) {
        this.studentService = studentService;
        this.attendanceRepository = attendanceRepository;
        this.marksRepository = marksRepository;
        this.resultRepository = resultRepository;
        this.assignmentRepository = assignmentRepository;
    }
    @GetMapping("/attendance")
    public Object attendance(Authentication auth) {
        return attendanceRepository.findByStudentId(studentService.getByUsername(auth.getName()).getId());
    }
    @GetMapping("/marks")
    public Object marks(Authentication auth) {
        return marksRepository.findByStudentId(studentService.getByUsername(auth.getName()).getId());
    }
    @GetMapping("/results")
    public Object results(Authentication auth) {
        return resultRepository.findByStudentId(studentService.getByUsername(auth.getName()).getId());
    }
    @GetMapping("/assignments")
    public Object assignments(Authentication auth) {
        var student = studentService.getByUsername(auth.getName());
        if (student.getCourse() == null) return java.util.List.of();
        return assignmentRepository.findAll();
    }
}
