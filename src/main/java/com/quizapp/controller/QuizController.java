package com.quizapp.controller;

import com.quizapp.dto.QuestionDTO;
import com.quizapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class QuizController {
    private final QuestionService questionService;

    @PostMapping("add-question")
    public ResponseEntity<?> createQuiz(@RequestBody QuestionDTO questionDTO) {
        QuestionDTO newQuestion = questionService.createQuiz(questionDTO);
        if(newQuestion == null) {
            return new ResponseEntity<>("Question not created", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
    }
}
