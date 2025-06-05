package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
