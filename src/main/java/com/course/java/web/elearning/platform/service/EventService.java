package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.EventDto;
import com.course.java.web.elearning.platform.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    List<Event> getAllEvents();

    Optional<Event> getEventById(Long id);

    List<Event> getAllEventsByUser(String username);

    Event saveEvent(EventDto event);

    void deleteEvent(Long id);
}
