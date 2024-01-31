package com.quizapp.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class QuestionDTO {
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private String category;
    private String difficulty;
    private LocalDateTime timestamp;
}
