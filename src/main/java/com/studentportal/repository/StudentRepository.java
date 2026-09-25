package com.studentportal.repository;
import com.studentportal.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUsername(String username);
    Optional<Student> findByRollNumber(String rollNumber);
    List<Student> findByCourseId(Long courseId);
}
