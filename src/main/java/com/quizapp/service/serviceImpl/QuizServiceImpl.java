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
        log.info("User Answers Map: " + userAnswers);


        for(Question question : questions) {
            Long questionId = question.getId();
            log.info("Question ID: " + questionId);

            if(userAnswers.containsKey(questionId)) {

                List<String> userAnswerList = Collections.singletonList(userAnswers.get(questionId).trim());
                log.info(userAnswerList.toString());
                log.info("User Answers Map Contains Question ID: " + userAnswers.containsKey(questionId));

                String correctAnswer = question.getAnswer();
                log.info(correctAnswer);
                log.info("I got here " + question.getQuestionType());

                if (question.getQuestionType() == Type.TRUE_FALSE || question.getQuestionType() == Type.SINGLE_CHOICE) {
                    String userAnswer = userAnswerList.get(0);

                    log.info(userAnswer);

                    if(userAnswer.equals(correctAnswer)) {
                        score++;
                    }
                }
                else if (question.getQuestionType() == Type.MULTI_CHOICE) {
                    log.info("I got here ");
                    log.info("I got here " + Arrays.stream(correctAnswer.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList()));
                    log.info("I got here " + userAnswerList);
                    if (userAnswerList.containsAll(Arrays.asList(correctAnswer.trim().split(",")))) {
                        score++;
                    }
                }
            }
        }
        log.info(String.valueOf(score));

        UserScore userScore = new UserScore();
        userScore.setUser(user);
        userScore.setScore(score);
        userScoreRepository.save(userScore);

        return score;
    }
}
