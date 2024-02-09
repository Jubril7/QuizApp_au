package com.quizapp.service;

import com.quizapp.dto.QuestionDTO;
import com.quizapp.entity.Question;


import java.nio.file.AccessDeniedException;
import java.util.List;

public interface QuestionService {

   QuestionDTO createQuiz(QuestionDTO questionDTO) throws AccessDeniedException;

   List<Question> getAllQuestion();
}
