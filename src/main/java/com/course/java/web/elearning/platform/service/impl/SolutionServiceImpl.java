package com.course.java.web.elearning.platform.service.impl;

import com.course.java.web.elearning.platform.dto.AssignmentDto;
import com.course.java.web.elearning.platform.entity.Assignment;
import com.course.java.web.elearning.platform.entity.Solution;
import com.course.java.web.elearning.platform.entity.User;
import com.course.java.web.elearning.platform.repository.SolutionRepository;
import com.course.java.web.elearning.platform.service.AssignmentsService;
import com.course.java.web.elearning.platform.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.course.java.web.elearning.platform.dto.mapper.EntityMapper.mapCreateDtoToEntity;

@Service
public class SolutionServiceImpl implements SolutionService {

    private final SolutionRepository solutionRepository;
    private final AssignmentsService assignmentService;

    @Autowired
    public SolutionServiceImpl(AssignmentsService assignmentService, SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
        this.assignmentService = assignmentService;
    }

    public Solution saveSolution(Solution solution) {
        return solutionRepository.save(solution);
    }

    public List<Solution> getSolutionsByAssignmentId(Long assignmentId) {
        return solutionRepository.findByAssignmentId(assignmentId);
    }

    public void deleteSolution(Long id) {
        solutionRepository.deleteById(id);
    }

    public boolean hasUserUploadedSolution(Long userId, Long assignmentId) {
        return solutionRepository.existsByUserIdAndAssignmentId(userId, assignmentId);
    }

    public void handleSolutionUpload(User user, Long assignmentId, MultipartFile file) throws IOException {
        if (hasUserUploadedSolution(user.getId(), assignmentId)) {
            throw new IllegalStateException("You have already uploaded a solution for this assignment.");
        }

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty. Please upload a valid file.");
        }

        String uploadDir = "uploads/solutions/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Solution solution = new Solution();
        solution.setFilePath(filePath.toString());
        AssignmentDto assignmentDto = assignmentService.getAssignmentById(assignmentId);
        Assignment assignment = mapCreateDtoToEntity(assignmentDto, Assignment.class);
        solution.setUser(user);
        solution.setAssignment(assignment);

        saveSolution(solution);
    }

}