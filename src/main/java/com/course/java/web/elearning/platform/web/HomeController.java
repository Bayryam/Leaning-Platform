package com.course.java.web.elearning.platform.web;

import com.course.java.web.elearning.platform.dto.AssignmentDto;
import com.course.java.web.elearning.platform.dto.mapper.EntityMapper;
import com.course.java.web.elearning.platform.entity.Assignment;
import com.course.java.web.elearning.platform.entity.Event;
import com.course.java.web.elearning.platform.entity.User;
import com.course.java.web.elearning.platform.service.AssignmentsService;
import com.course.java.web.elearning.platform.service.CourseService;
import com.course.java.web.elearning.platform.service.EventService;
import com.course.java.web.elearning.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final EventService eventService;
    private final UserService userService;
    private final CourseService courseService;
    private final AssignmentsService assignmentService;

    @Autowired
    public HomeController(EventService eventService, CourseService courseService, UserService userService, AssignmentsService assignmentService) {
        this.eventService = eventService;
        this.courseService = courseService;
        this.userService = userService;
        this.assignmentService = assignmentService;
    }

    @GetMapping({"/", "/home"})
    public String homePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            User loggedUser = userService.getUserByUsername(userDetails.getUsername());
            if (loggedUser != null && loggedUser.isAdmin()) {
                return "redirect:/admin/users";
            }

            if (loggedUser != null && loggedUser.hasRole("ROLE_INSTRUCTOR")) {
                return "redirect:/instructor/courses";
            }
            model.addAttribute("loggedUser", loggedUser);
        }

        List<Event> allEvents = eventService.getAllEvents();

        List<Event> upcomingEvents = allEvents.stream()
                .filter(event -> event.getStartTime().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Event::getStartTime))
                .limit(3)
                .collect(Collectors.toList());

        model.addAttribute("events", allEvents);
        model.addAttribute("upcomingEvents", upcomingEvents);

        CourseController.getTop3CoursesGroupedByCategory(model, courseService);

        List<AssignmentDto> assignmentDtos = assignmentService.getAllAssignments();

        List<Assignment> assignments = assignmentDtos.stream()
                .map(dto -> EntityMapper.mapCreateDtoToEntity(dto, Assignment.class))
                .toList();

        List<Assignment> upcomingAssignments = assignments.stream()
                .filter(a -> a.getDueDate().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Assignment::getDueDate))
                .limit(3)
                .collect(Collectors.toList());

        model.addAttribute("assignments", upcomingAssignments);
        if (userDetails == null) {
            return "redirect:/courses";
        }

        return "home";
    }
}
