package com.studentportal.repository;
import com.studentportal.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByStudentId(Long studentId);
    List<QuizAttempt> findByQuizId(Long quizId);
    Optional<QuizAttempt> findByQuizIdAndStudentId(Long quizId, Long studentId);
}
