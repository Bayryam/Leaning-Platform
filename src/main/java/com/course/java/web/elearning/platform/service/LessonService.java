package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.LessonDto;
import com.course.java.web.elearning.platform.entity.Course;
import com.course.java.web.elearning.platform.entity.Lesson;

public interface LessonService {
    Lesson addLesson(LessonDto lesson, Course course);

    Lesson getLessonById(Long lessonId);

    Lesson updateLessonDetails(Course course, Long id, String detail, Object value);

    Lesson save(Lesson lesson);
}