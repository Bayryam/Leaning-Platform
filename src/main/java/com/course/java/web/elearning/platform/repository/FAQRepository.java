package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
