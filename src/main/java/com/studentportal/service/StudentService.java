package com.studentportal.service;
import com.studentportal.entity.Student;
import com.studentportal.exception.ResourceNotFoundException;
import com.studentportal.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final AttendenceRepository attendanceRepository;
    private final AssignmentRepository assignmentRepository;
    private final MarksRepository marksRepository;
    private final ResultRepository resultRepository;

    public StudentService(StudentRepository studentRepository, SubjectRepository subjectRepository,
                          AttendenceRepository attendanceRepository, AssignmentRepository assignmentRepository,
                          MarksRepository marksRepository, ResultRepository resultRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.attendanceRepository = attendanceRepository;
        this.assignmentRepository = assignmentRepository;
        this.marksRepository = marksRepository;
        this.resultRepository = resultRepository;
    }

    public Student getByUsername(String username) {
        return studentRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student updateProfile(String username, Student incoming) {
        Student s = getByUsername(username);
        s.setFullName(incoming.getFullName());
        s.setPhone(incoming.getPhone());
        return studentRepository.save(s);
    }

    public Object dashboard(String username) {
        Student s = getByUsername(username);
        return java.util.Map.of(
                "student", s,
                "subjects", s.getCourse() == null ? List.of() : subjectRepository.findByCourseId(s.getCourse().getId()),
                "attendance", attendanceRepository.findByStudentId(s.getId()),
                "assignments", s.getCourse() == null ? List.of() : assignmentRepository.findAll(),
                "marks", marksRepository.findByStudentId(s.getId()),
                "results", resultRepository.findByStudentId(s.getId())
        );
    }
}
