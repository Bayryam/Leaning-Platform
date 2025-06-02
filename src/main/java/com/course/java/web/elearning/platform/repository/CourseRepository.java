package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.Course;
import com.course.java.web.elearning.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByName(String name);
    @Query("SELECT c FROM Course c JOIN c.categories cat WHERE cat = :category")
    List<Course> findAllByCategory(String category);
    List<Course> findAllByCreatedBy(User user);
}
