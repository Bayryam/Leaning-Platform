package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.CourseDto;
import com.course.java.web.elearning.platform.dto.QuestionDto;
import com.course.java.web.elearning.platform.dto.mapper.CourseDtoToCourseMapper;
import com.course.java.web.elearning.platform.dto.mapper.EntityMapper;
import com.course.java.web.elearning.platform.entity.*;
import com.course.java.web.elearning.platform.exception.DuplicatedEntityException;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.repository.CourseRepository;
import com.course.java.web.elearning.platform.repository.QuestionRepository;
import com.course.java.web.elearning.platform.repository.StudentResultRepository;
import com.course.java.web.elearning.platform.repository.UserRepository;
import com.course.java.web.elearning.platform.service.CourseService;
import com.course.java.web.elearning.platform.service.UserService;
import com.course.java.web.elearning.platform.wrapper.QuestionWrapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;
    private final StudentResultRepository studentResultRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final UserService userService;


    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             QuestionRepository questionRepository,
                             StudentResultRepository studentResultRepository,
                             EntityManager entityManager,
                             UserRepository userRepository,
                             UserService userService) {
        this.courseRepository = courseRepository;
        this.questionRepository = questionRepository;
        this.studentResultRepository = studentResultRepository;
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    public Course addCourse(CourseDto courseDto, User user) {
        if (courseRepository.existsByName(courseDto.getName())) {
            throw new DuplicatedEntityException(String.format(
                    "Cannot create a course with name '%s' because it already exists. Please choose other name.",
                    courseDto.getName()));
        }

        Course course = CourseDtoToCourseMapper.mapCourseDtoToCourse(courseDto, user);
        return courseRepository.save(course);
    }

    @Override
    public Map<String, Set<Course>> getCoursesGroupedByCategory() {
        final List<Course> allCourses = courseRepository.findAll();

        return allCourses.stream()
                .flatMap(course -> course.getCategories().stream()
                        .map(category -> new AbstractMap.SimpleEntry<>(category, course)))
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.mapping(
                                AbstractMap.SimpleEntry::getValue,
                                Collectors.toSet()
                        )
                ));
    }

    @Override
    public List<Course> getCoursesByCategory(String category) {
        return courseRepository.findAllByCategory(category);
    }

    @Override
    public List<Course> getAllInProgressCoursesByUser(Long id) {
        List<Course> completedCourses = findCompletedCoursesByUserId(id);
        return userRepository.findStartedCoursesByUserId(id).stream()
                .filter(course -> !completedCourses.contains(course))
                .toList();
    }

    @Override
    public List<Course> getAllCoursesByUser(User user) {
        return courseRepository.findAllByCreatedBy(user);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no such course!"));
    }

    @Override
    public Long getCourseQuizId(long courseId) {
        var course = getCourseById(courseId);
        return course.getQuiz().getId();
    }

    @Override
    public Course getCourseById(long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Course with id %s not found", courseId),
                        "redirect:/home"));
    }

    @Transactional
    @Override
    public Course addQuestionToCourse(Long courseId, QuestionDto questionDto) {
        Course course = getCourseById(courseId);
        var question = questionRepository.save(EntityMapper.mapCreateDtoToEntity(questionDto, Question.class));
        course.getQuestions().add(question);
        return courseRepository.save(course);
    }

    @Transactional
    @Override
    public Course addQuizToCourse(long courseId, Quiz quiz) {
        Course course = getCourseById(courseId);
        course.setQuiz(quiz);
        return courseRepository.saveAndFlush(course);
    }

    @Transactional
    @Override
    public List<QuestionWrapper> getQuestionsForCourseQuiz(Long courseId) {
        Course course = getCourseById(courseId);
        Quiz quiz = course.getQuiz();

        if (quiz != null) {
            List<Question> questionsDB = quiz.getQuestions();

            return questionsDB.stream()
                    .map(question -> new QuestionWrapper(question.getId(), question.getQuestionTitle(),
                            question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4()))
                    .toList();
        } else {
            throw new EntityNotFoundException("There is no quiz available for that course.");
        }
    }

    @Override
    public List<Question> getAllQuestionsForCourse(Long courseId) {
        Course course = getCourseById(courseId);

        if (course != null) {
            return course.getQuestions();
        } else {
            throw new EntityNotFoundException("There is no quiz available for that course.");
        }
    }

    @Override
    public Course updateCourseDetails(Long id, String detail, Object value) {
        Course existingCourse = getCourseById(id);
        switch (detail) {
            case "course-name" -> existingCourse.setName((String) value);
            case "course-description" -> existingCourse.setDescription((String) value);
            case "add-category" -> existingCourse.addCategory((String) value);
            default -> throw new IllegalArgumentException("Invalid user detail: " + detail);
        }
        save(existingCourse);
        return existingCourse;
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
    }

    @Override
    public void addNewStudentResult(int percentage, long elapsedTime, long courseId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Course course = getCourseById(courseId);
        if (percentage >= 80) {
            course.addStudentCompletedCourse(user);
            Course savedCourse = save(course);
            user.addCompletedCourse(savedCourse);
            userService.save(user);
        }
        var highScores = course.getHighScores();
        if (highScores.stream().filter(score -> score.getUsername().equals(username)).count() == 0) {
            var sResult = new StudentResult();
            sResult.setUsername(username);
            sResult.setPercentage(percentage);
            sResult.setElapsedTime(elapsedTime);
            var studentResult = studentResultRepository.save(sResult);
            course.getHighScores().add(studentResult);
            courseRepository.save(course);
            return;
        }

        if (isNewStudentRecord(percentage, elapsedTime, username, course.getHighScores())) {
            StudentResult result = course.getHighScores().stream()
                    .filter(score -> score.getUsername().equals(username))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Student result not found"));
            studentResultRepository.updateStudentResult(result.getId(), percentage, elapsedTime);
            studentResultRepository.flush();
            entityManager.clear(); // Clear the persistence context
            var updatedResult = studentResultRepository.findById(result.getId()).orElseThrow(() -> new EntityNotFoundException("Student result not found"));
            course.getHighScores().removeIf(score -> score.getId().equals(updatedResult.getId()));
            course.getHighScores().add(updatedResult);
            courseRepository.save(course);
        }
    }

    private boolean isNewStudentRecord(int currentPercentage, long elapsedTime, String username, List<StudentResult> highScores) {
        return highScores.stream()
                .filter(score -> score.getUsername().equals(username))
                .anyMatch(score -> score.getPercentage() < currentPercentage || score.getPercentage() == currentPercentage && score.getElapsedTime() > elapsedTime);
    }

    public List<Question> getQuestionsForCourse(Long courseId) {
        Course course = getCourseById(courseId);
        return course.getQuestions();
    }

    @Override
    public List<StudentResult> getHighScoresForCourse(Long courseId) {
        Course course = getCourseById(courseId);
        return course.getHighScores().stream()
                .sorted((a, b) -> {
                    int percentageComparison = Integer.compare(b.getPercentage(), a.getPercentage());
                    if (percentageComparison != 0) {
                        return percentageComparison;
                    }
                    return Long.compare(a.getElapsedTime(), b.getElapsedTime());
                })
                .toList();
    }

    @Override
    public List<Course> findCompletedCoursesByUserId(Long id) {
        return userRepository.findCompletedCoursesByUserId(id);
    }

    @Override
    public Course startCourse(Long courseId, User user) {
        Course course = getCourseById(courseId);
        course.addParticipant(user);
        return courseRepository.save(course);
    }

    @Override
    public Course completeCourse(Course course, User user) {
        course.removeParticipant(user);
        course.addStudentCompletedCourse(user);
        return courseRepository.save(course);
    }
}
