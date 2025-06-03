package com.course.java.web.elearning.platform.service;

import com.course.java.web.elearning.platform.dto.AssignmentDto;

import java.util.List;

public interface AssignmentsService {
    List<AssignmentDto> getAllAssignments();
    List<AssignmentDto> getAssignmentsByCourseId(Long courseId);
    AssignmentDto getAssignmentById(Long id);
    AssignmentDto saveAssignment(AssignmentDto assignmentDto);
    void deleteAssignment(Long id);
}
