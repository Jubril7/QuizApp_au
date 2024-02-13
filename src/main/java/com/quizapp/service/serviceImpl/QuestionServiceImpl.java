package com.quizapp.service.serviceImpl;

import com.quizapp.dto.QuestionDTO;
import com.quizapp.entity.Question;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;


    @Override
    public QuestionDTO createQuiz(QuestionDTO questionDTO) throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication != null &&
                authentication.isAuthenticated() &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new AccessDeniedException("You are not authorized to create a quiz");
        }
        Question question = Question.builder()
                .questionText(questionDTO.getQuestionText())
                .option1(questionDTO.getOption1())
                .option2(questionDTO.getOption2())
                .option3(questionDTO.getOption3())
                .option4(questionDTO.getOption4())
                .answer(questionDTO.getAnswer())
                .difficulty(questionDTO.getDifficulty())
                .category(questionDTO.getCategory())
                .questionType(questionDTO.getQuestionType())
                .build();
        questionRepository.save(question);
        return questionDTO;
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }
}
