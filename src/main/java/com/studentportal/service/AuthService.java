package com.studentportal.service;

import com.studentportal.dto.LoginRequest;
import com.studentportal.dto.RegistrationRequest;
import com.studentportal.entity.Admin;
import com.studentportal.entity.Faculty;
import com.studentportal.entity.Student;
import com.studentportal.entity.User;
import com.studentportal.enums.Role;
import com.studentportal.repository.AdminRepository;
import com.studentportal.repository.CourseRepository;
import com.studentportal.repository.DepartmentRepository;
import com.studentportal.repository.FacultyRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.UserRepository;
import com.studentportal.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final AdminRepository adminRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            FacultyRepository facultyRepository,
            AdminRepository adminRepository,
            DepartmentRepository departmentRepository,
            CourseRepository courseRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.adminRepository = adminRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public String register(RegistrationRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (request.role() == Role.STUDENT) {

            Student student = new Student();

            fillUser(student, request);

            student.setRollNumber(request.rollNumber());
            student.setFullName(request.fullName());
            student.setSemester(request.semester());

            if (request.departmentId() != null) {
                student.setDepartment(
                        departmentRepository.findById(request.departmentId())
                                .orElseThrow(() ->
                                        new IllegalArgumentException("Department not found"))
                );
            }

            if (request.courseId() != null) {
                student.setCourse(
                        courseRepository.findById(request.courseId())
                                .orElseThrow(() ->
                                        new IllegalArgumentException("Course not found"))
                );
            }

            studentRepository.save(student);

        } else if (request.role() == Role.FACULTY) {

            Faculty faculty = new Faculty();

            fillUser(faculty, request);

            faculty.setFullName(request.fullName());

            if (request.departmentId() != null) {
                faculty.setDepartment(
                        departmentRepository.findById(request.departmentId())
                                .orElseThrow(() ->
                                        new IllegalArgumentException("Department not found"))
                );
            }

            facultyRepository.save(faculty);

        } else {

            Admin admin = new Admin();

            fillUser(admin, request);

            adminRepository.save(admin);
        }

        return "Registration successful";
    }

    public String login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = userRepository
                .findByUsername(request.username())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );
    }

    private void fillUser(User user, RegistrationRequest request) {

        user.setUsername(request.username());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setEnabled(true);
    }
}