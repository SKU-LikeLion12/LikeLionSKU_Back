package likelion.sku_sku.repository;

import likelion.sku_sku.domain.JoinAssignmentFiles;
import likelion.sku_sku.domain.SubmitAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinAssignmentFilesRepository extends JpaRepository<JoinAssignmentFiles, Long> {

    // 제출된 과제로 제출된 과제 파일 삭제
    void deleteBySubmitAssignment(SubmitAssignment submitAssignment);

    // 제출된 과제의 제출된 과제 파일 리스트 반환
    List<JoinAssignmentFiles> findBySubmitAssignment(SubmitAssignment submitAssignment);
}

