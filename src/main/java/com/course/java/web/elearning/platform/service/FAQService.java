package com.course.java.web.elearning.platform.service;

import java.util.List;

import com.course.java.web.elearning.platform.dto.FAQDto;
import com.course.java.web.elearning.platform.entity.FAQ;

public interface FAQService {
    FAQ addQuestion(FAQDto faqDto);

    List<FAQ> getAllQuestions();

    FAQ deleteQuestion(Long id);
}
