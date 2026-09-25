package com.studentportal.controller;

import com.studentportal.entity.*;
import com.studentportal.repository.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@PreAuthorize("hasRole('FACULTY')")
@RequestMapping("/api/faculty")
public class FacultyController {

    private final FacultyRepository facultyRepository;
    private final AttendenceRepository attendanceRepository;
    private final MarksRepository marksRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public FacultyController(
            FacultyRepository facultyRepository,
            AttendenceRepository attendanceRepository,
            MarksRepository marksRepository,
            AssignmentRepository assignmentRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository) {

        this.facultyRepository = facultyRepository;
        this.attendanceRepository = attendanceRepository;
        this.marksRepository = marksRepository;
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/profile/{id}")
    public Faculty getProfile(@PathVariable Long id) {

        return facultyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));
    }

    @PutMapping("/profile/{id}")
    public Faculty updateProfile(
            @PathVariable Long id,
            @RequestBody Faculty updatedFaculty) {

        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));

        if (updatedFaculty.getFullName() != null) {
            faculty.setFullName(updatedFaculty.getFullName());
        }

        if (updatedFaculty.getDepartment() != null) {
            faculty.setDepartment(updatedFaculty.getDepartment());
        }

        return facultyRepository.save(faculty);
    }

    @GetMapping("/{facultyId}/subjects")
    public List<Subject> getAssignedSubjects(
            @PathVariable Long facultyId) {

        return subjectRepository.findByFacultyId(facultyId);
    }
    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/subjects/{subjectId}/students")
    public List<Student> getStudentsBySubject(
            @PathVariable Long subjectId) {

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        if (subject.getCourse() == null) {
            throw new RuntimeException(
                    "Subject is not assigned to a course");
        }

        return studentRepository.findByCourseId(
                subject.getCourse().getId());
    }

    @PostMapping("/attendance")
    public Attendance markAttendance(
            @RequestParam Long studentId,
            @RequestParam Long subjectId,
            @RequestParam boolean present) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        Attendance attendance = new Attendance();

        attendance.setStudent(student);
        attendance.setSubject(subject);
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setPresent(present);

        return attendanceRepository.save(attendance);
    }

    @GetMapping("/attendance/{subjectId}")
    public List<Attendance> getAttendance(
            @PathVariable Long subjectId) {

        return attendanceRepository.findBySubjectId(subjectId);
    }

    @PostMapping("/marks")
    public Marks addMarks(
            @RequestParam Long studentId,
            @RequestParam Long subjectId,
            @RequestParam String examType,
            @RequestParam double obtainedMarks,
            @RequestParam double totalMarks) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));
        Marks marks = new Marks();
        marks.setStudent(student);
        marks.setSubject(subject);
        marks.setExamType(examType);
        marks.setObtainedMarks(obtainedMarks);
        marks.setTotalMarks(totalMarks);

        return marksRepository.save(marks);
    }

    @GetMapping("/marks/{subjectId}")
    public List<Marks> getMarks(
            @PathVariable Long subjectId) {

        return marksRepository.findBySubjectId(subjectId);
    }
    @PostMapping("/assignments")
    public Assignment createAssignment(
            @RequestParam Long facultyId,
            @RequestParam Long subjectId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String dueAt) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));
        Assignment assignment = new Assignment();
        assignment.setFaculty(faculty);
        assignment.setSubject(subject);
        assignment.setTitle(title);
        assignment.setDescription(description);

        assignment.setDueDate(
                LocalDate.parse(dueAt)
        );
        return assignmentRepository.save(assignment);
    }
    @GetMapping("/assignments/{facultyId}")
    public List<Assignment> getAssignments(
            @PathVariable Long facultyId) {
        return assignmentRepository.findByFacultyId(facultyId);
    }
}