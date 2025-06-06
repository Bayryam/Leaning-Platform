package com.course.java.web.elearning.platform.repository;

import com.course.java.web.elearning.platform.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
