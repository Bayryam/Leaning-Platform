package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.entity.Question;
import com.course.java.web.elearning.platform.entity.Quiz;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuestionService {
    ResponseEntity<List<Question>> getAllQuestions();

    Question getQuestionById(long id);

    void deleteQuestionFromQuiz(long id);
}
