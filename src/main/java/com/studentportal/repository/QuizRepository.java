package com.studentportal.repository;

import com.studentportal.entity.Quiz;
import com.studentportal.enums.QuizStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByStatus(QuizStatus status);
    List<Quiz> findByCreatedById(Long facultyId);
}
