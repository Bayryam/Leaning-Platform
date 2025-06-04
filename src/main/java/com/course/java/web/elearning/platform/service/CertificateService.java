package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.entity.Course;
import org.springframework.stereotype.Service;

@Service
public interface CertificateService {
    void issueCertificate(String username, Course course, int scorePercentage);
}