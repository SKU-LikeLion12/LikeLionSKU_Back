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
    List<SubmitAssignment> findByWriter(String writer);

    List<SubmitAssignment> findByAssignment_Track(TrackType track);
    int countByWriterAndAssignment_AssignmentStatus(String writer, AssignmentStatus assignmentStatus);
    int countByWriterAndAssignment_AssignmentStatusAndSubmitStatus(String writer, AssignmentStatus assignmentStatus, SubmitStatus submitStatus);

    List<SubmitAssignment> findByAssignment_TrackAndWriter(TrackType track, String writer);
    List<SubmitAssignment> findDistinctWriterByAssignment_Track(TrackType track);

    Optional<SubmitAssignment> findByWriterAndAssignment(String writer, Assignment assignment);

    int countByAssignment_TrackAndWriterAndAssignment_AssignmentStatus(TrackType track, String writer, AssignmentStatus assignmentStatus);
    int countByAssignment_TrackAndWriterAndAssignment_AssignmentStatusAndSubmitStatus(TrackType track, String writer, AssignmentStatus assignmentStatus, SubmitStatus submitStatus);

}