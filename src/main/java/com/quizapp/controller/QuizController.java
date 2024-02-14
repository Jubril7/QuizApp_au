package com.quizapp.controller;

import com.quizapp.dto.UserAnswersDTO;
import com.quizapp.dto.UserScoreDTO;
import com.quizapp.entity.User;
import com.quizapp.exception.AnswerEvaluationException;
import com.quizapp.service.Jwt.UserDetailsServiceImpl;
import com.quizapp.service.QuizService;
import com.quizapp.service.UserScoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/v1/quiz/")
@SecurityRequirement(name = "bearerAuth")
public class QuizController {

    private final QuizService quizService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserScoreService userScoreService;



    @PostMapping("submit-answers")
    public ResponseEntity<?> submitAnswers(@RequestBody UserAnswersDTO userAnswersDTO) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDetailsService.loadUserByUsername(userDetails.getUsername());

            Map<Long, String> userAnswers = userAnswersDTO.getUserAnswers();
            int score = quizService.evaluateAnswers(userAnswers, user);

            return ResponseEntity.ok("Quiz submitted successfully. Your score: " + score);
        } catch (Exception e) {
            throw new AnswerEvaluationException("Failed to evaluate user answers");
        }
    }

    @GetMapping("quiz-scores")
    public ResponseEntity<?> getQuizScores() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userDetailsService.loadUserByUsername(userDetails.getUsername());

            List<UserScoreDTO> userScores = userScoreService.getUserScores(user);

            return ResponseEntity.ok(userScores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }

    }
}
