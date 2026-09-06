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
}