package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.SubmitAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findFeedbackBySubmitAssignment(SubmitAssignment submitAssignment);
    Optional<Feedback> findFeedbackBySubmitAssignment_Id(Long submitAssignmentId);
}
