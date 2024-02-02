package com.quizapp.service.serviceImpl;

import com.quizapp.entity.Question;
import com.quizapp.entity.User;
import com.quizapp.entity.UserScore;
import com.quizapp.repository.UserScoreRepository;
import com.quizapp.service.QuestionService;
import com.quizapp.service.QuizService;
import lombok.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final UserScoreRepository userScoreRepository;
    private final QuestionService questionService;

    @Override
    public int evaluateAnswers(Map<Long, String> userAnswers, User user) {
        int score = 0;

        List<Question> questions = questionService.getAllQuestion();

        for(Question question : questions) {
            Long questionId = question.getId();
            if(userAnswers.containsKey(questionId)) {
                String userAnswer = userAnswers.get(questionId);
                if(userAnswer.equals(question.getAnswer())) {
                    score++;
                }
            }
        }
        UserScore userScore = new UserScore();
        userScore.setUser(user);
        userScore.setScore(score);
        userScoreRepository.save(userScore);

        return score;
    }
}
