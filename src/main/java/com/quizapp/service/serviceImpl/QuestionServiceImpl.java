package com.quizapp.service.serviceImpl;

import com.quizapp.dto.QuestionDTO;
import com.quizapp.entity.Question;
import com.quizapp.repository.QuestionRepository;
import com.quizapp.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    @Override
    public QuestionDTO createQuiz(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionText(questionDTO.getQuestionText());
        question.setOption1(questionDTO.getOption1());
        question.setOption2(questionDTO.getOption2());
        question.setOption3(questionDTO.getOption3());
        question.setOption4(questionDTO.getOption4());
        question.setAnswer(questionDTO.getAnswer());
        question.setDifficulty(questionDTO.getDifficulty());
        question.setCategory(questionDTO.getCategory());
        questionRepository.save(question);
        return questionDTO;
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }
}
