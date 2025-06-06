package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByTitle(String title);
    List<Event> findByInstructor(String instructor);
}
