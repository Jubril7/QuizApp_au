package com.quizapp.service.serviceImpl;

import com.quizapp.entity.Question;
import com.quizapp.entity.User;
import com.quizapp.repository.UserScoreRepository;
import com.quizapp.service.QuestionService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class QuizServiceImplTest {

    @Test
    void evaluateAnswers_SingleChoice_Correct() {
        UserScoreRepository userScoreRepository = mock(UserScoreRepository.class);
        QuestionService questionService = mock(QuestionService.class);

        QuizServiceImpl quizService = new QuizServiceImpl(userScoreRepository, questionService);

        Map<Long, String> userAnswers = new HashMap<>();
        userAnswers.put(1L, "True");

        Question question = new Question();
        question.setId(1L);
        question.setAnswer("True");

        User user = new User();

        when(questionService.getAllQuestion()).thenReturn(List.of(question));

        int score = quizService.evaluateAnswers(userAnswers, user);

        assertEquals(1, score);
    }

    @Test
    void evaluateAnswers_MultiChoice_Correct() {
        UserScoreRepository userScoreRepository = mock(UserScoreRepository.class);
        QuestionService questionService = mock(QuestionService.class);

        QuizServiceImpl quizService = new QuizServiceImpl(userScoreRepository, questionService);

        Map<Long, String> userAnswers = new HashMap<>();
        userAnswers.put(1L, "A,B");

        Question question = new Question();
        question.setId(1L);
        question.setAnswer("A,B");

        User user = new User();

        when(questionService.getAllQuestion()).thenReturn(List.of(question));

        int score = quizService.evaluateAnswers(userAnswers, user);

        assertEquals(1, score);
    }

    @Test
    void evaluateAnswers_SingleChoice_Incorrect() {
        UserScoreRepository userScoreRepository = mock(UserScoreRepository.class);
        QuestionService questionService = mock(QuestionService.class);

        QuizServiceImpl quizService = new QuizServiceImpl(userScoreRepository, questionService);

        Map<Long, String> userAnswers = new HashMap<>();
        userAnswers.put(1L, "False");

        Question question = new Question();
        question.setId(1L);
        question.setAnswer("True");

        User user = new User();

        when(questionService.getAllQuestion()).thenReturn(List.of(question));

        int score = quizService.evaluateAnswers(userAnswers, user);

        assertEquals(0, score);
    }

    @Test
    void evaluateAnswers_MultiChoice_Incorrect() {
        UserScoreRepository userScoreRepository = mock(UserScoreRepository.class);
        QuestionService questionService = mock(QuestionService.class);

        QuizServiceImpl quizService = new QuizServiceImpl(userScoreRepository, questionService);

        Map<Long, String> userAnswers = new HashMap<>();
        userAnswers.put(1L, "C,D");

        Question question = new Question();
        question.setId(1L);
        question.setAnswer("A,B");

        User user = new User();

        when(questionService.getAllQuestion()).thenReturn(List.of(question));

        int score = quizService.evaluateAnswers(userAnswers, user);

        assertEquals(0, score);
    }

}