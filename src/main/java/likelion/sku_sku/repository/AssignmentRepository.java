package likelion.sku_sku.repository;

import likelion.sku_sku.domain.SubmitAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<SubmitAssignment, Long> {
}
