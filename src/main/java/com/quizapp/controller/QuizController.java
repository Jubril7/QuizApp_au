package com.quizapp.controller;

import com.quizapp.dto.UserAnswersDTO;
import com.quizapp.dto.UserScoreDTO;
import com.quizapp.entity.User;
import com.quizapp.service.Jwt.UserDetailsServiceImpl;
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

    private final QuizService quizService;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserScoreService userScoreService;



    @PostMapping("submit-answers")
    public ResponseEntity<?> submitAnswers(@RequestBody UserAnswersDTO userAnswersDTO) {

        System.out.println("Received JSON: " + userAnswersDTO.toString());


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) userDetailsService.loadUserByUsername(userDetails.getUsername());

        Map<Long, String> userAnswers = userAnswersDTO.getUserAnswers();
        int score = quizService.evaluateAnswers(userAnswers, user);


        return ResponseEntity.ok("Quiz submitted successfully. Your score: " + score);
    }



    @GetMapping("quiz-scores")
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
