package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.entity.Certificate;
import com.course.java.web.elearning.platform.entity.Course;
import com.course.java.web.elearning.platform.entity.User;
import com.course.java.web.elearning.platform.repository.CertificateRepository;
import com.course.java.web.elearning.platform.service.CertificateService;
import com.course.java.web.elearning.platform.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final UserService userService;

    public CertificateServiceImpl(CertificateRepository certificateRepository, UserService userService) {
        this.certificateRepository = certificateRepository;
        this.userService = userService;
    }

    public void issueCertificate(String username, Course course, int scorePercentage) {
        if (scorePercentage >= 80) {
            User user = userService.getUserByUsername(username);

            Certificate certificate = new Certificate();
            certificate.setCourseName(course.getName());
            certificate.setIssuedTo(user);
            certificate.setScorePercentage(scorePercentage);
            certificate.setIssuedOn(Date.from(Instant.now()));

            Certificate savedCertificate = certificateRepository.save(certificate);
            user.addCertificate(savedCertificate);
            userService.save(user);
        }
    }
}
