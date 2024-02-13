package com.quizapp.service.serviceImpl;

import com.quizapp.entity.Question;
import com.quizapp.entity.User;
import com.quizapp.entity.UserScore;
import com.quizapp.repository.UserScoreRepository;
import com.quizapp.service.QuestionService;
import com.quizapp.service.QuizService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
        for (Question question : questions) {
            Long questionId = question.getId();

            if (userAnswers.containsKey(questionId)) {
                List<String> userAnswerList = List.of(userAnswers.get(questionId));

                switch (question.getQuestionType()) {
                    case TRUE_FALSE, SINGLE_CHOICE ->
                            score += evaluateSingleChoice(userAnswerList, question.getAnswer());
                    case MULTI_CHOICE -> score += evaluateMultiChoice(userAnswerList, question.getAnswer());
                    default -> {
                    }

                }
            }
        }
        UserScore userScore = UserScore.builder()
                .user(user)
                .score(score)
                .build();
        userScoreRepository.save(userScore);

        return score;
    }

    private int evaluateSingleChoice(List<String> userAnswerList, String correctAnswer) {
        return userAnswerList.stream()
                .filter(userAnswer -> userAnswer.equals(correctAnswer))
                .findAny()
                .map(ignored -> 1)
                .orElse(0);
    }

    private int evaluateMultiChoice(List<String> userAnswerList, String correctAnswer) {
        List<String> newCorrectAnswer = List.of(correctAnswer);
        return new HashSet<>(userAnswerList).containsAll(newCorrectAnswer) ? 1 : 0;
    }
}
