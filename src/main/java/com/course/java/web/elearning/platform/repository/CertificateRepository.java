package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
