package com.quizapp.dto;

import lombok.Data;

import java.util.Map;
@Data
public class UserAnswersDTO {
    private Map<Long, String> userAnswers;
}
