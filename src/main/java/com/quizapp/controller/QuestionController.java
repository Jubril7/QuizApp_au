package com.quizapp.controller;

import com.quizapp.dto.QuestionDTO;
import com.quizapp.entity.Question;
import com.quizapp.service.QuestionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/")
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("all-questions")
    public ResponseEntity<?> getAllQuestions() {
        try {
            List<Question> allQuestions = questionService.getAllQuestion();
            return new ResponseEntity<>(allQuestions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve questions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("add-question")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createQuiz(@RequestBody QuestionDTO questionDTO) {
        try {
            QuestionDTO newQuestion = questionService.createQuiz(questionDTO);
            if (newQuestion == null) {
                return new ResponseEntity<>("Question not created", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>("Access denied: Unable to create question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
