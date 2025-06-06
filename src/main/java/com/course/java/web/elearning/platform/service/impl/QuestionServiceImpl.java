package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.entity.Course;
import com.course.java.web.elearning.platform.entity.Question;
import com.course.java.web.elearning.platform.entity.Quiz;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.repository.QuestionRepository;
import com.course.java.web.elearning.platform.service.CourseService;
import com.course.java.web.elearning.platform.service.QuestionService;
import com.course.java.web.elearning.platform.service.QuizzesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizzesService quizzesService;
    private final CourseService courseService;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuizzesService quizzesService, CourseService courseService) {
        this.questionRepository = questionRepository;
        this.quizzesService = quizzesService;
        this.courseService = courseService;
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

    public void deleteQuestionFromCourse(long courseId, long questionId) {
        Question question = getQuestionById(questionId);
        Course course = courseService.getCourseById(courseId);
        course.getQuestions().remove(question);
        courseService.save(course);
    }
}
