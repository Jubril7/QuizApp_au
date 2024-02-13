package com.quizapp.service.serviceImpl;

import com.quizapp.entity.Question;
import com.quizapp.entity.User;
import com.quizapp.entity.UserScore;
import com.quizapp.enums.Type;
import com.quizapp.repository.UserScoreRepository;
import com.quizapp.service.QuestionService;
import com.quizapp.service.QuizService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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

                List<String> userAnswerList = Collections.singletonList(userAnswers.get(questionId).trim());

                String correctAnswer = question.getAnswer();

                if (question.getQuestionType() == Type.TRUE_FALSE || question.getQuestionType() == Type.SINGLE_CHOICE) {
                    String userAnswer = userAnswerList.get(0);
                    if(userAnswer.equals(correctAnswer)) {
                        score++;
                    }
                }
                else if (question.getQuestionType() == Type.MULTI_CHOICE) {
                    List<String> newCorrectAnswer = List.of(correctAnswer);
                    if (userAnswerList.containsAll(newCorrectAnswer)) {
                        score++;
                    }
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
