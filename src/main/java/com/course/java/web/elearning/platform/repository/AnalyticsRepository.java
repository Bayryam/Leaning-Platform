package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.CourseAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends JpaRepository<CourseAnalytics, Long> {
}
