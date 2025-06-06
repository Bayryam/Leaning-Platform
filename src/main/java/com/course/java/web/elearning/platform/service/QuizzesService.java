package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.QuizDto;
import com.course.java.web.elearning.platform.entity.Course;
import com.course.java.web.elearning.platform.entity.Question;
import com.course.java.web.elearning.platform.entity.Quiz;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.wrapper.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public interface QuizzesService {
    Quiz createQuiz(QuizDto quizDto, List<Question> quizQuestions);

    Quiz getQuizById(long id);

    ResponseEntity<Map<String, Integer>> calculateQuizResult(long courseId, long quizId, List<Response> answers, long elapsedTime);

    void deleteQuestionFromQuiz(long quizId, Question question);

    Quiz getQuizForQuestion(long id);

    Course addQuizToCourse(long courseId, QuizDto quizDto);

}
