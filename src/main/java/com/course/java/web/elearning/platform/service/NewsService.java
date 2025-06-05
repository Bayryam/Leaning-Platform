package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.entity.News;

import java.util.List;

public interface NewsService {

    List<News> getAllNews();
    void deleteNews(Long id) ;
    News getNewsById(Long id);
}
