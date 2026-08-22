package com.studentportal.repository;
import com.studentportal.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendenceRepository extends JpaRepository<Attendance,Long>{
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByStudentIdAndSubject(Long studentId , Long subjectId);
}