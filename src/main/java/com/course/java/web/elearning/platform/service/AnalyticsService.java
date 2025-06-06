package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.entity.Course;
import com.course.java.web.elearning.platform.entity.StudentResult;

import java.util.List;

public interface AnalyticsService {
    void addNewParticipantAnalytics(Course course, StudentResult newStudentResult, List<StudentResult> highScores);

    void addNewHighScoreInAnalytics(Course course, StudentResult newStudentResult, List<StudentResult> highScores);
}
