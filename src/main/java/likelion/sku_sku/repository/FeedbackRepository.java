package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
