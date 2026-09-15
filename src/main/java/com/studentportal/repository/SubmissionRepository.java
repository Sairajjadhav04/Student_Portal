package com.studentportal.repository;

import com.studentportal.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByStudentId(Long studentId);

    List<Submission> findByStudentIdAndAssignmentId(
            Long studentId,
            Long assignmentId
    );

}