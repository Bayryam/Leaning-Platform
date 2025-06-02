package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
