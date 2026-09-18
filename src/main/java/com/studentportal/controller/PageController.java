package com.studentportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/student/dashboard")
    public String dashboard() {
        return "Student/dashboard";
    }

    @GetMapping("/student/profile")
    public String profile() {
        return "Student/profile";
    }

    @GetMapping("/student/subjects")
    public String subjects() {
        return "Student/subjects";
    }

    @GetMapping("/student/attendance")
    public String attendance() {
        return "Student/attendance";
    }

    @GetMapping("/student/marks")
    public String marks() {
        return "Student/marks";
    }

    @GetMapping("/student/results")
    public String results() {
        return "Student/results";
    }

    @GetMapping("/student/assignments")
    public String assignments() {
        return "Student/assignments";
    }


    @GetMapping("/faculty/dashboard")
    public String facultyDashboard() {
        return "Faculty/dashboard";
    }

    @GetMapping("/faculty/profile")
    public String facultyProfile() {
        return "Faculty/profile";
    }

    @GetMapping("/faculty/students")
    public String facultyStudents() {
        return "Faculty/students";
    }

    @GetMapping("/faculty/subjects")
    public String facultySubjects() {
        return "Faculty/subjects";
    }

    @GetMapping("/faculty/attendance")
    public String facultyAttendance() {
        return "Faculty/attendance";
    }

    @GetMapping("/faculty/marks")
    public String facultyMarks() {
        return "Faculty/marks";
    }

    @GetMapping("/faculty/assignments")
    public String facultyAssignments() {
        return "Faculty/assignments";
    }


    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Admin/dashboard";
    }

    @GetMapping("/admin/profile")
    public String adminProfile() {
        return "Admin/profile";
    }

    @GetMapping("/admin/students")
    public String adminStudents() {
        return "Admin/students";
    }

    @GetMapping("/admin/faculty")
    public String adminFaculty() {
        return "Admin/faculty";
    }

    @GetMapping("/admin/subjects")
    public String adminSubjects() {
        return "Admin/subjects";
    }

    @GetMapping("/admin/reports")
    public String adminReports() {
        return "Admin/reports";
    }

    @GetMapping("/faculty/quizzes")
    public String facultyQuizzes() {
        return "Faculty/quizzes";
    }

    @GetMapping("/faculty/quiz/create")
    public String createQuiz() {
        return "Faculty/create-quiz";
    }

    @GetMapping("/faculty/quiz/manage")
    public String manageQuiz() {
        return "Faculty/manage-quiz";
    }

    @GetMapping("/student/quizzes")
    public String studentQuizzes() {
        return "Student/quizzes";
    }

    @GetMapping("/student/quiz/instructions")
    public String quizInstructions() {
        return "Student/quiz-instructions";
    }

    @GetMapping("/student/quiz/attempt")
    public String attemptQuiz() {
        return "Student/attempt-quiz";
    }

    @GetMapping("/student/quiz/result")
    public String quizResult() {
        return "Student/quiz-result";
    }

    @GetMapping("/admin/quizzes")
    public String adminQuizzes() {
        return "Admin/quizzes";
    }

    @GetMapping("/admin/quiz/manage")
    public String adminManageQuiz() {
        return "Admin/manage-quiz";
    }

}
