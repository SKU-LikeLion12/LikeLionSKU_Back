package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByTrackAndAssignmentStatus(TrackType track, AssignmentStatus assignmentStatus);

    List<Assignment> findByTrack(TrackType trackType);
    List<Assignment> findByAssignmentStatus(AssignmentStatus status);
    int countByAssignmentStatus(AssignmentStatus assignmentStatus);


}
