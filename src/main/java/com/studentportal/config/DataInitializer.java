package com.studentportal.config;

import com.studentportal.entity.Department;
import com.studentportal.repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeDepartments(
            DepartmentRepository departmentRepository) {

        return args -> {

            if (departmentRepository.count() == 0) {

                Department aiDs = new Department();
                aiDs.setName("BE AI & DS");
                departmentRepository.save(aiDs);

                Department computer = new Department();
                computer.setName("BE Computer Engineering");
                departmentRepository.save(computer);

                Department mechanical = new Department();
                mechanical.setName("BE Mechanical Engineering");
                departmentRepository.save(mechanical);

                Department entc = new Department();
                entc.setName(
                        "BE Electronics and Telecommunication Engineering"
                );
                departmentRepository.save(entc);

                Department it = new Department();
                it.setName("BE Information Technology");
                departmentRepository.save(it);

            }
        };
    }
}