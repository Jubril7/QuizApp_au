package com.quizapp.service.serviceImpl;

import com.quizapp.dto.QuestionDTO;
import com.quizapp.entity.Question;
import com.quizapp.enums.Type;
import com.quizapp.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.file.AccessDeniedException;
import java.util.*;

import static com.quizapp.enums.Type.SINGLE_CHOICE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    @Test
    void createQuiz_admin()  {
        QuestionRepository questionRepository = mock(QuestionRepository.class);
        QuestionServiceImpl questionService = new QuestionServiceImpl(questionRepository);

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionText("The Earth is flat");
        questionDTO.setOption1("True");
        questionDTO.setOption2("False");
        questionDTO.setOption3(null);
        questionDTO.setOption4(null);
        questionDTO.setAnswer("True");
        questionDTO.setDifficulty("Easy");
        questionDTO.setCategory("Geography");
        questionDTO.setQuestionType(Type.valueOf("TRUE_FALSE"));

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        when(authentication.getAuthorities()).thenAnswer((Answer<Collection<? extends GrantedAuthority>>) invocation -> Collections.singletonList(adminAuthority));
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertDoesNotThrow(() -> questionService.createQuiz(questionDTO));

        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void createQuiz_NonAdmin() {
        QuestionRepository questionRepository = mock(QuestionRepository.class);
        QuestionServiceImpl questionService = new QuestionServiceImpl(questionRepository);

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionText("What is the capital of France?");
        questionDTO.setOption1("London");
        questionDTO.setOption2("Paris");
        questionDTO.setOption3("Berlin");
        questionDTO.setOption4("Madrid");
        questionDTO.setAnswer("Paris");
        questionDTO.setDifficulty("Easy");
        questionDTO.setCategory("Geography");
        questionDTO.setQuestionType(Type.valueOf(String.valueOf(SINGLE_CHOICE)));

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getAuthorities()).thenReturn(new ArrayList<>());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(AccessDeniedException.class, () -> questionService.createQuiz(questionDTO));

        verify(questionRepository, never()).save(any(Question.class));
    }

    @Test
    void getAllQuestion() {

        QuestionRepository questionRepository = mock(QuestionRepository.class);
        QuestionServiceImpl questionService = new QuestionServiceImpl(questionRepository);

        List<Question> questions = new ArrayList<>();
        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> result = questionService.getAllQuestion();

        verify(questionRepository, times(1)).findAll();

        assertEquals(questions, result);
    }
}