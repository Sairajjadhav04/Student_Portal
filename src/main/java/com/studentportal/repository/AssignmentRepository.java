package com.studentportal.repository;
import com.studentportal.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findBySubjectId(Long subjectId);
    List<Assignment> findByFacultyId(Long facultyId);
}
