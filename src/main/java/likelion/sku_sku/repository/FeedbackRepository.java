package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.SubmitAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findFeedbackBySubmitAssignment(SubmitAssignment submitAssignment);
}
