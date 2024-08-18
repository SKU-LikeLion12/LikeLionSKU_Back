package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByTrackAndAssignmentStatus(TrackType track, AssignmentStatus assignmentStatus);
    List<Assignment> findByAssignmentStatusAndCreateDateBefore(AssignmentStatus status, LocalDate date);

    List<Assignment> findAssignmentsByAssignmentStatusAndTrack(AssignmentStatus status, TrackType track);
    int countByAssignmentStatusAndTrack(AssignmentStatus assignmentStatus, TrackType track);

    int countByTrack(TrackType trackType);

}
