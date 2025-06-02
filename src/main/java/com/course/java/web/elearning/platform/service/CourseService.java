package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.CourseDto;
import com.course.java.web.elearning.platform.dto.QuestionDto;
import com.course.java.web.elearning.platform.entity.*;
import com.course.java.web.elearning.platform.wrapper.QuestionWrapper;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CourseService {
    Course addCourse(CourseDto course, User user);

    Map<String, Set<Course>> getCoursesGroupedByCategory();

    List<Course> getCoursesByCategory(String category);

    List<Course> getAllInProgressCoursesByUser(Long id);

    List<Course> getAllCoursesByUser(User user);

    Course getCourseById(Long id);

    Long getCourseQuizId(long courseId);

    Course getCourseById(long courseId);

    Course addQuestionToCourse(Long courseId, QuestionDto questionDto);

    @Transactional
    Course addQuizToCourse(long courseId, Quiz quiz);

    List<QuestionWrapper> getQuestionsForCourseQuiz(Long courseId);

    List<Question> getAllQuestionsForCourse(Long courseId);

    Course save(Course course);

    List<Course> getAllCourses();

    Course findById(Long courseId);

    Course startCourse(Long courseId, User user);

    Course completeCourse(Course course, User user);

    void addNewStudentResult(int percentage, long elapsedTime, long courseId);

    List<StudentResult> getHighScoresForCourse(Long courseId);

    Course updateCourseDetails(Long id, String detail, Object value);

    List<Course> findCompletedCoursesByUserId(Long id);
}

