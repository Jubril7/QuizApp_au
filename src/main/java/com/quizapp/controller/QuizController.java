package com.quizapp.controller;

import com.quizapp.dto.QuestionDTO;
import com.quizapp.dto.UserAnswersDTO;
import com.quizapp.dto.UserScoreDTO;
import com.quizapp.entity.Question;
import com.quizapp.entity.User;
import com.quizapp.service.Jwt.UserDetailsServiceImpl;
import com.quizapp.service.QuestionService;
import com.quizapp.service.QuizService;
import com.quizapp.service.UserScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class QuizController {

    private final QuestionService questionService;
    private final QuizService quizService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserScoreService userScoreService;

    @PostMapping("add-question")
    public ResponseEntity<?> createQuiz(@RequestBody QuestionDTO questionDTO) {
        QuestionDTO newQuestion = questionService.createQuiz(questionDTO);
        if(newQuestion == null) {
            return new ResponseEntity<>("Question not created", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
    }

    @PostMapping("submit-answers")
    public ResponseEntity<?> submitAnswers(@RequestBody UserAnswersDTO userAnswersDTO) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userDetailsService.loadUserByUsername(userDetails.getUsername());

        Map<Long, String> userAnswers = userAnswersDTO.getUserAnswers();
        int score = quizService.evaluateAnswers(userAnswers, user);


        return ResponseEntity.ok("Quiz submitted successfully. Your score: " + score);
    }

    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> allQuestions = questionService.getAllQuestion();
        return new ResponseEntity<>(allQuestions, HttpStatus.OK);
    }

    @GetMapping("/quiz-scores")
    public ResponseEntity<?> getQuizScores() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = (User) userDetailsService.loadUserByUsername(userDetails.getUsername());

            List<UserScoreDTO> userScores = userScoreService.getUserScores(user);

            return ResponseEntity.ok(userScores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }

    }
}
