package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.TicketDto;
import com.course.java.web.elearning.platform.entity.Ticket;

import java.util.List;

public interface TicketService {

  List<Ticket> getAllTickets();
  Ticket saveTicket(TicketDto ticketDto, Long courseId, String issuerName);
  Ticket resolveTicket(Long ticketId);
}
