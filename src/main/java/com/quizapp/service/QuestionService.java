package com.quizapp.service;

import com.quizapp.dto.QuestionDTO;
import com.quizapp.entity.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
   QuestionDTO createQuiz(QuestionDTO questionDTO);

   List<Question> getAllQuestion();
}
