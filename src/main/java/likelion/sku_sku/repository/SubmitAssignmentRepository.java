package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmitAssignmentRepository extends JpaRepository<SubmitAssignment, Long> {

    // 아기사자 이름과 강의 안내물 id로 제출된 과제 반환
    Optional<SubmitAssignment> findByWriterAndAssignment_Id(String writer, Long assignmentId);

    // 아기사자 이름과 강의 안내물로 제출된 과제 반환
    Optional<SubmitAssignment> findByWriterAndAssignment(String writer, Assignment assignment);

    // 아기사자 이름과 트랙, 통과 상태 별 제출된 과제 개수 반환
    int countByWriterAndAssignment_TrackAndPassNonePass(String writer, TrackType trackType, PassNonePass passNonePass);
}