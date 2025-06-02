package com.course.java.web.elearning.platform.dto.mapper;

import com.course.java.web.elearning.platform.dto.CourseDto;
import com.course.java.web.elearning.platform.entity.Course;
import com.course.java.web.elearning.platform.entity.User;

import java.util.Date;

public class CourseDtoToCourseMapper {
    public static Course mapCourseDtoToCourse(CourseDto courseDto, User user) {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setCategories(courseDto.getCategories());
        course.setCreatedBy(user);
        course.setCreatedOn(new Date());

        return course;
    }
}