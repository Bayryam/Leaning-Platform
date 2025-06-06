package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.entity.User;
import com.course.java.web.elearning.platform.wrapper.ProgressInfo;

import java.util.Map;

public interface CourseDashboardService {
    Map<User, ProgressInfo> getUserProgressInCourse(Long courseId);
}
