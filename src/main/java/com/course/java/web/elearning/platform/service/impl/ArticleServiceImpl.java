package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.ArticleDto;
import com.course.java.web.elearning.platform.dto.mapper.ArticleDtoToArticleMapper;
import com.course.java.web.elearning.platform.entity.Article;
import com.course.java.web.elearning.platform.entity.User;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.repository.ArticleRepository;
import com.course.java.web.elearning.platform.service.ArticleService;
import com.course.java.web.elearning.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    @Override
    public Article createArticle(Long groupId, ArticleDto articleDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.getUserByUsername(username);
        Article article = ArticleDtoToArticleMapper.mapArticleDtoToArticle(articleDto, user);
        return articleRepository.save(article);
    }

    @Transactional
    @Override
    public Article deleteArticleById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            throw new EntityNotFoundException(String.format("Article with id=%s not found", id), "redirect:/groups");
        }

        articleRepository.delete(article);
        return article;
    }
}
