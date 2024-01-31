package com.quizapp.service;

import com.quizapp.dto.QuestionDTO;
import org.springframework.http.ResponseEntity;

public interface QuestionService {
   QuestionDTO createQuiz(QuestionDTO questionDTO);

}
