package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
