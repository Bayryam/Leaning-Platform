package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.ArticleDto;
import com.course.java.web.elearning.platform.entity.Article;

import java.util.List;

public interface ArticleService {
  Article createArticle(Long groupId, ArticleDto article);
  Article deleteArticleById(Long id);
}
