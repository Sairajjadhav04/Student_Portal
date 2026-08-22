package com.studentportal.service;

import com.studentportal.entity.*;
import com.studentportal.exception.ResourceNotFoundException;
import com.studentportal.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final AssignmentRepository assignmentRepository;
    private final AttendenceRepository attendenceRepository;
    private final MarksRepository marksRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository,
                          SubjectRepository subjectRepository, AssignmentRepository assignmentRepository,
                          AttendenceRepository attendanceRepository, MarksRepository marksRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.assignmentRepository = assignmentRepository;
        this.attendenceRepository = attendanceRepository;
        this.marksRepository = marksRepository;
    }

    public Faculty getByUsername(String username) {
        return facultyRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
    }

    public List<Student> students() {
        return studentRepository.findAll();
    }

    public List<Subject> subjects(String username) {
        Faculty f = getByUsername(username);
        return subjectRepository.findAll().stream()
                .filter(s -> s.getFaculty() != null && s.getFaculty().getId().equals(f.getId()))
                .toList();
    }

    public Assignment createAssignment(String username, Assignment a) {
        Faculty f = getByUsername(username);
        a.setFaculty(f);
        return assignmentRepository.save(a);
    }

    public Attendance saveAttendance(Attendance a) {
        return attendenceRepository.save(a);
    }

    public Marks saveMarks(Marks m) {
        return marksRepository.save(m);
    }
}
