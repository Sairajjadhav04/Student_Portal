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
        return "student/student-dashboard";
    }
    @GetMapping("/student/profile")
    public String profile() {
        return "student/profile";
    }
    @GetMapping("/student/subjects")
    public String subjects() {
        return "student/subjects";
    }
    @GetMapping("/student/attendance")
    public String attendance() {
        return "student/attendance";
    }
    @GetMapping("/student/marks")
    public String marks() {
        return "student/marks";
    }
    @GetMapping("/student/results")
    public String results() {
        return "student/results";
    }
    @GetMapping("/student/assignments")
    public String assignments() {
        return "student/assignments";
    }
    @GetMapping("/student/quiz")
    public String quiz() {
        return "student/quiz";
    }
}