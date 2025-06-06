package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByName(String name);
}
