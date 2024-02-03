package com.quizapp.controller;

import com.quizapp.dto.QuestionDTO;
import com.quizapp.entity.Question;
import com.quizapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> allQuestions = questionService.getAllQuestion();
        return new ResponseEntity<>(allQuestions, HttpStatus.OK);
    }

    @PostMapping("add-question")
    public ResponseEntity<?> createQuiz(@RequestBody QuestionDTO questionDTO) {
        QuestionDTO newQuestion = questionService.createQuiz(questionDTO);
        if(newQuestion == null) {
            return new ResponseEntity<>("Question not created", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
    }
}
