package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.ArticleDto;
import com.course.java.web.elearning.platform.dto.mapper.ArticleDtoToArticleMapper;
import com.course.java.web.elearning.platform.entity.Article;
import com.course.java.web.elearning.platform.entity.Group;
import com.course.java.web.elearning.platform.entity.Image;
import com.course.java.web.elearning.platform.entity.User;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.repository.ArticleRepository;
import com.course.java.web.elearning.platform.service.ArticleService;
import com.course.java.web.elearning.platform.service.GroupService;
import com.course.java.web.elearning.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, GroupService groupService, UserService userService) {
        this.articleRepository = articleRepository;
        this.groupService = groupService;
        this.userService = userService;
    }

    @Override
    public Article createArticle(Long groupId, ArticleDto articleDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.getUserByUsername(username);
        Group group = groupService.getGroupById(groupId);
        Article article = ArticleDtoToArticleMapper.mapArticleDtoToArticle(articleDto, user, group);
        return articleRepository.save(article);
    }

    @Override
    public List<Article> getAllArticlesForAGroup(Long groupId) {
        List<Article> articles = articleRepository.findByGroupId(groupId) != null ? articleRepository.findByGroupId(groupId) : List.of();
        return articles.stream().peek(article -> {
            User author = article.getAuthor();
            Image profilePicture = author.getProfilePicture();
            if (profilePicture != null) {
                author.setProfilePictureBase64(profilePicture.parseImage());
            }
        }).toList();
    }

    @Transactional
    @Override
    public Article deleteArticleById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            throw new EntityNotFoundException(String.format("Article with id=%s not found", id), "redirect:/groups");
        }

        Group group = article.getGroup();
        if (group != null) {
            group.getArticles().remove(article);
            article.setGroup(null);
        }

        articleRepository.delete(article);
        return article;
    }
}
