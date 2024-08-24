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

    // 아기사자 이름과 제출된 과제 상태별 제출된 과제 리스트 반환
    List<SubmitAssignment> findByWriterAndAssignment_AssignmentStatus(String writer, AssignmentStatus status);

    // 트랙별 중복된 작성자를 제외한 제출된 과제 리스트 반환
    List<SubmitAssignment> findDistinctWriterByAssignment_Track(TrackType track);

    // 아기사자 이름과 과제 상태, 트랙, 과제 제출 상태별 제출된 과제 개수 반환
    int countByWriterAndAssignment_AssignmentStatusAndAssignment_TrackAndSubmitStatus(String writer, AssignmentStatus assignmentStatus, TrackType trackType, SubmitStatus submitStatus);

    // 아기사자 이름과 트랙, 통과 상태 별 제출된 과제 개수 반환
    int countByWriterAndAssignment_TrackAndPassNonePass(String writer, TrackType trackType, PassNonePass passNonePass);

}