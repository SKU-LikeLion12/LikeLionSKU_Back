package likelion.sku_sku.repository;

import likelion.sku_sku.domain.SubmitAssignment;
import org.hibernate.sql.ast.tree.update.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmitAssignmentRepository extends JpaRepository<SubmitAssignment, Long> {
}
