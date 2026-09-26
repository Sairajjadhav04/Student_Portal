package com.studentportal.config;

import com.studentportal.entity.Admin;
import com.studentportal.entity.Faculty;
import com.studentportal.enums.Role;
import com.studentportal.repository.AdminRepository;
import com.studentportal.repository.FacultyRepository;
import com.studentportal.repository.UserRepository;
import com.studentportal.security.JwtAuthenticationFilter;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    // =====================================================
    // CREATE DEFAULT FACULTY AND ADMIN ACCOUNTS
    // =====================================================

    @Bean
    public CommandLineRunner createDefaultUsers(
            UserRepository userRepository,
            FacultyRepository facultyRepository,
            AdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // ================= FACULTY =================

            if (userRepository.findByUsername("faculty1").isEmpty()) {

                Faculty faculty = new Faculty();

                faculty.setUsername("faculty1");
                faculty.setPassword(
                        passwordEncoder.encode("Faculty@123")
                );
                faculty.setEmail("faculty1@gmail.com");
                faculty.setRole(Role.FACULTY);
                faculty.setEnabled(true);
                faculty.setFullName("Faculty One");

                facultyRepository.save(faculty);

                System.out.println(
                        "Default Faculty account created: faculty1"
                );
            }


            // ================= ADMIN =================

            if (userRepository.findByUsername("admin1").isEmpty()) {

                Admin admin = new Admin();

                admin.setUsername("admin1");
                admin.setPassword(
                        passwordEncoder.encode("Admin@123")
                );
                admin.setEmail("admin1@gmail.com");
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);

                adminRepository.save(admin);

                System.out.println(
                        "Default Admin account created: admin1"
                );
            }
        };
    }

    // =====================================================
    // SECURITY FILTER
    // =====================================================

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/login.html",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/api/auth/**",
                                "/student/**",
                                "/faculty/**",
                                "/admin/**",
                                "/error"
                        )
                        .permitAll()

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // =====================================================
    // CORS
    // =====================================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(
                List.of(
                        "http://localhost:3000",
                        "http://localhost:5500"
                )
        );

        config.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                )
        );

        config.setAllowedHeaders(
                List.of("*")
        );

        config.setExposedHeaders(
                List.of("Authorization")
        );

        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                config
        );

        return source;
    }
}