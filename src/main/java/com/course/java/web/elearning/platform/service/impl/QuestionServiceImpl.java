package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.entity.Question;
import com.course.java.web.elearning.platform.entity.Quiz;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.repository.QuestionRepository;
import com.course.java.web.elearning.platform.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizzesServiceImpl quizzesService;


    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuizzesServiceImpl quizzesService) {
        this.questionRepository = questionRepository;
        this.quizzesService = quizzesService;
    }

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
    }

    public Question getQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Question with id %s not found", id)));
    }

    public void deleteQuestionFromQuiz(long id) {
        Quiz quizForQuestion = quizzesService.getQuizForQuestion(id);
        Question question = getQuestionById(id);
        if (quizForQuestion != null) {
            quizzesService.deleteQuestionFromQuiz(quizForQuestion.getId(), question);
        }
        questionRepository.delete(question);
    }
}
