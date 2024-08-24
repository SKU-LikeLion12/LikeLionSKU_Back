package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.SubmitAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // 제출된 과제의 피드백 반환
    Optional<Feedback> findFeedbackBySubmitAssignment(SubmitAssignment submitAssignment);
}
