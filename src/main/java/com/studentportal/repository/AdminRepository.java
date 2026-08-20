package com.studentportal.repository;
import com.studentportal.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AdminRepository extends JpaRepository<Admin, Long> {}
