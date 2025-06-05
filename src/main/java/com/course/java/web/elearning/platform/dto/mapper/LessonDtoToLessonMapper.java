package com.course.java.web.elearning.platform.dto.mapper;

import com.course.java.web.elearning.platform.dto.LessonDto;
import com.course.java.web.elearning.platform.entity.Course;
import com.course.java.web.elearning.platform.entity.Lesson;

import java.util.Date;

public class LessonDtoToLessonMapper {
    public static Lesson mapLessonDtoToLesson(LessonDto lessonDto, Course course) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDto.getTitle());
        lesson.setContent(lessonDto.getContent());
        lesson.setCreatedOn(new Date());
        lesson.setRelatedCourse(course);

        return lesson;
    }
}