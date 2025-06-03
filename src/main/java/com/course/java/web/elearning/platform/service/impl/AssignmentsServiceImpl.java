
package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.AssignmentDto;
import com.course.java.web.elearning.platform.entity.Assignment;
import com.course.java.web.elearning.platform.entity.Course;
import com.course.java.web.elearning.platform.exception.EntityNotFoundException;
import com.course.java.web.elearning.platform.repository.AssignmentsRepository;
import com.course.java.web.elearning.platform.repository.CourseRepository;
import com.course.java.web.elearning.platform.service.AssignmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentsServiceImpl implements AssignmentsService {
    private final AssignmentsRepository assignmentsRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public AssignmentsServiceImpl(AssignmentsRepository assignmentRepository, CourseRepository courseRepository) {
        this.assignmentsRepository = assignmentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<AssignmentDto> getAllAssignments() {
        return assignmentsRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentDto> getAssignmentsByCourseId(Long courseId) {
        return assignmentsRepository.findByCourseId(courseId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentDto getAssignmentById(Long id) {
        return assignmentsRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found"));
    }

    @Override
    public AssignmentDto saveAssignment(AssignmentDto assignmentDto) {
        final Assignment assignment = new Assignment();
        assignment.setTitle(assignmentDto.getTitle());
        assignment.setDescription(assignmentDto.getDescription());
        assignment.setDueDate(assignmentDto.getDueDate());

        if (assignmentDto.getCourseId() != null) {
            Course course = courseRepository.findById(assignmentDto.getCourseId())
                    .orElseThrow(() -> new EntityNotFoundException("Course not found"));
            assignment.setCourse(course);
        }

        final Assignment savedAssignment = assignmentsRepository.save(assignment);
        return mapToDto(savedAssignment);
    }

    @Override
    public void deleteAssignment(Long id) {
        assignmentsRepository.deleteById(id);
    }

    private AssignmentDto mapToDto(Assignment assignment) {
        return new AssignmentDto(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getDueDate(),
                assignment.getCourse() != null ? assignment.getCourse().getId() : null
        );
    }
}