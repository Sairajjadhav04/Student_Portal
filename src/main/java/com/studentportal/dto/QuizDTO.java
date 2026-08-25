package com.studentportal.dto;

import java.time.LocalDateTime;
import java.util.List;

public record QuizDTO(
        String title,
        String description,
        Integer durationMinutes,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Long subjectId,
        List<QuestionDTO> questions
) {
    public record QuestionDTO(
            String questionText,
            Double marks,
            List<OptionDTO> options
    ) {}
    public record OptionDTO(
            String optionText,
            boolean correct
    ) {}
}
