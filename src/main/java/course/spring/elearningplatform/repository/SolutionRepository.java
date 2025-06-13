package course.spring.elearningplatform.repository;

import course.spring.elearningplatform.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    List<Solution> findByAssignmentId(Long assignmentId);

    boolean existsByUserIdAndAssignmentId(Long userId, Long assignmentId);
}
