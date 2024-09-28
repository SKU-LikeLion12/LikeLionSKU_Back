package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    // 트랙과 과제 안내물 상태로 Assignment 리스트 반환
    List<Assignment> findByTrackAndAssignmentStatus(TrackType track, AssignmentStatus assignmentStatus);

    // 트랙으로 과제 안내물 리스트 내림차순 반환
    List<Assignment> findByTrackOrderByIdDesc(TrackType trackType);

    // 과제 안내물 상태와 트랙별 과제 안내물 개수 반환
    int countByAssignmentStatusAndTrack(AssignmentStatus assignmentStatus, TrackType track);

    // 트랙별 과제 안내물 개수 반환
    int countByTrack(TrackType trackType);
}
