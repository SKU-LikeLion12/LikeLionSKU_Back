package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmitAssignmentRepository extends JpaRepository<SubmitAssignment, Long> {
    List<SubmitAssignment> findByWriterAndAssignment_AssignmentStatus(String writer, AssignmentStatus status);
    int countByWriterAndAssignment_AssignmentStatusAndAssignment_TrackAndSubmitStatus(String writer, AssignmentStatus assignmentStatus, TrackType trackType, SubmitStatus submitStatus);

    Optional<SubmitAssignment> findByWriterAndAssignment_Id(String writer, Long assignmentId);
    List<SubmitAssignment> findDistinctWriterByAssignment_Track(TrackType track);

    Optional<SubmitAssignment> findByWriterAndAssignment(String writer, Assignment assignment);

    int countByAssignment_TrackAndWriterAndAssignment_AssignmentStatus(TrackType track, String writer, AssignmentStatus assignmentStatus);
    int countByAssignment_TrackAndWriterAndAssignment_AssignmentStatusAndSubmitStatus(TrackType track, String writer, AssignmentStatus assignmentStatus, SubmitStatus submitStatus);

}