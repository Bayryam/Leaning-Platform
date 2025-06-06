package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.FAQDto;
import com.course.java.web.elearning.platform.dto.mapper.EntityMapper;
import com.course.java.web.elearning.platform.entity.FAQ;
import com.course.java.web.elearning.platform.repository.FAQRepository;
import com.course.java.web.elearning.platform.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQServiceImpl implements FAQService {
    private final FAQRepository faqRepository;

    @Autowired
    public FAQServiceImpl(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public FAQ addQuestion(FAQDto faqDto) {
        FAQ faq = EntityMapper.mapCreateDtoToEntity(faqDto, FAQ.class);
        faqRepository.save(faq);
        return faq;
    }

    public FAQ deleteQuestion(Long id) {
        FAQ faq = faqRepository.findById(id).orElse(null);
        faqRepository.deleteById(id);
        return faq;
    }

    public List<FAQ> getAllQuestions() {
        return faqRepository.findAll();
    }
}
