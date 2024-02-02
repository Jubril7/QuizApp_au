package com.quizapp.service;

import com.quizapp.entity.User;
import java.util.Map;

public interface QuizService {
    int evaluateAnswers(Map<Long, String> userAnswers, User user);

}
