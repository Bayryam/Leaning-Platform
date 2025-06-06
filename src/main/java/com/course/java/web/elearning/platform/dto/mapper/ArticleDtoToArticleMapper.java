package com.course.java.web.elearning.platform.dto.mapper;

import com.course.java.web.elearning.platform.dto.ArticleDto;
import com.course.java.web.elearning.platform.entity.Article;
import com.course.java.web.elearning.platform.entity.User;

import java.time.LocalDateTime;

public class ArticleDtoToArticleMapper {
    public static Article mapArticleDtoToArticle(ArticleDto articleDto, User user, Group group) {
        Article article = new Article();
        article.setCreatedAt(LocalDateTime.now());
        article.setContent(articleDto.getContent());
        article.setGroup(group);
        article.setAuthor(user);
        return article;
    }
}
