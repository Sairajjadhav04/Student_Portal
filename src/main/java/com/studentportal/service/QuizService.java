package com.studentportal.service;

import com.studentportal.dto.QuizDTO;
import com.studentportal.entity.*;
import com.studentportal.enums.QuizStatus;
import com.studentportal.exception.ResourceNotFoundException;
import com.studentportal.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository attemptRepository;
    private final QuizAnswerRepository answerRepository;
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository,
                       QuizAttemptRepository attemptRepository, QuizAnswerRepository answerRepository,
                       FacultyRepository facultyRepository, StudentRepository studentRepository,
                       SubjectRepository subjectRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
        this.answerRepository = answerRepository;
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public Quiz createQuiz(String facultyUsername, QuizDTO dto) {
        Faculty faculty = facultyRepository.findByUsername(facultyUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        Quiz q = new Quiz();
        q.setTitle(dto.title());
        q.setDescription(dto.description());
        q.setDurationMinutes(dto.durationMinutes());
        q.setStartTime(dto.startTime());
        q.setEndTime(dto.endTime());
        q.setCreatedBy(faculty);
        q.setStatus(QuizStatus.DRAFT);

        if (dto.subjectId() != null)
            q.setSubject(subjectRepository.findById(dto.subjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subject not found")));

        double total = 0;
        if (dto.questions() != null) {
            for (QuizDTO.QuestionDTO qd : dto.questions()) {
                Question question = new Question();
                question.setQuestionText(qd.questionText());
                question.setMarks(qd.marks() == null ? 1.0 : qd.marks());
                question.setQuiz(q);

                if (qd.options() != null) {
                    for (QuizDTO.OptionDTO od : qd.options()) {
                        Option option = new Option();
                        option.setOptionText(od.optionText());
                        option.setCorrect(od.correct());
                        option.setQuestion(question);
                        question.getOptions().add(option);
                    }
                }
                q.getQuestions().add(question);
                total += question.getMarks();
            }
        }
        q.setTotalMarks(total);
        return quizRepository.save(q);
    }

    public List<Quiz> publishedQuizzes() {
        return quizRepository.findByStatus(QuizStatus.PUBLISHED);
    }

    public List<Quiz> facultyQuizzes(String username) {
        Faculty f = facultyRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        return quizRepository.findByCreatedById(f.getId());
    }

    public Quiz getQuiz(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
    }

    public Quiz publish(Long id, String username) {
        Quiz q = getQuiz(id);
        if (!q.getCreatedBy().getUsername().equals(username))
            throw new IllegalArgumentException("You can publish only your own quiz");
        q.setStatus(QuizStatus.PUBLISHED);
        return quizRepository.save(q);
    }

    public Quiz close(Long id, String username) {
        Quiz q = getQuiz(id);
        if (!q.getCreatedBy().getUsername().equals(username))
            throw new IllegalArgumentException("You can close only your own quiz");
        q.setStatus(QuizStatus.CLOSED);
        return quizRepository.save(q);
    }

    @Transactional
    public QuizAttempt startAttempt(Long quizId, String studentUsername) {
        Student student = studentRepository.findByUsername(studentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Quiz quiz = getQuiz(quizId);

        if (quiz.getStatus() != QuizStatus.PUBLISHED)
            throw new IllegalArgumentException("Quiz is not published");

        Optional<QuizAttempt> old = attemptRepository.findByQuizIdAndStudentId(quizId, student.getId());
        if (old.isPresent() && old.get().getSubmittedAt() != null)
            throw new IllegalArgumentException("Quiz already attempted");

        if (quiz.getStartTime() != null && LocalDateTime.now().isBefore(quiz.getStartTime()))
            throw new IllegalArgumentException("Quiz has not started");
        if (quiz.getEndTime() != null && LocalDateTime.now().isAfter(quiz.getEndTime()))
            throw new IllegalArgumentException("Quiz has ended");

        QuizAttempt attempt = old.orElseGet(QuizAttempt::new);
        attempt.setQuiz(quiz);
        attempt.setStudent(student);
        attempt.setStartedAt(LocalDateTime.now());
        attempt.setScore(0.0);
        return attemptRepository.save(attempt);
    }

    @Transactional
    public QuizAttempt submit(Long attemptId, String username, Map<Long, Long> answers) {
        QuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new ResourceNotFoundException("Attempt not found"));

        if (!attempt.getStudent().getUsername().equals(username))
            throw new IllegalArgumentException("This attempt does not belong to you");

        if (attempt.getSubmittedAt() != null)
            throw new IllegalArgumentException("Attempt already submitted");

        double score = 0;

        for (Map.Entry<Long, Long> entry : answers.entrySet()) {
            Question question = questionRepository.findById(entry.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

            Option selected = question.getOptions().stream()
                    .filter(o -> o.getId().equals(entry.getValue()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid option"));

            QuizAnswer answer = new QuizAnswer();
            answer.setAttempt(attempt);
            answer.setQuestion(question);
            answer.setSelectedOption(selected);
            answerRepository.save(answer);

            if (selected.isCorrect())
                score += question.getMarks();
        }

        attempt.setScore(score);
        attempt.setSubmittedAt(LocalDateTime.now());
        return attemptRepository.save(attempt);
    }

    public List<QuizAttempt> myAttempts(String username) {
        Student s = studentRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return attemptRepository.findByStudentId(s.getId());
    }

    public List<QuizAttempt> quizReports(Long quizId, String facultyUsername) {
        Quiz q = getQuiz(quizId);
        if (!q.getCreatedBy().getUsername().equals(facultyUsername))
            throw new IllegalArgumentException("Access denied");
        return attemptRepository.findByQuizId(quizId);
    }

    public List<Quiz> allQuizzes() {
        return quizRepository.findAll();
    }
}
