package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
