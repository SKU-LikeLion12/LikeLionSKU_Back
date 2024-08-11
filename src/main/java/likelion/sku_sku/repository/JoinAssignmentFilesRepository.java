package likelion.sku_sku.repository;

import likelion.sku_sku.domain.JoinAssignmentFiles;
import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.domain.SubmitAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinAssignmentFilesRepository extends JpaRepository<JoinAssignmentFiles, Long> {
    void deleteBySubmitAssignment(SubmitAssignment submitAssignment);
}

