package likelion.sku_sku.service;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.exception.InvalidListIdException;
import likelion.sku_sku.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

//    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // 매일 자정에 실행
//    @Transactional
//    public void updateAssignmentStatus() {
//        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
//
//        // TODAY인 상태 && 2일이 지난 과제
//        List<Assignment> todayAssignments = assignmentRepository.findByAssignmentStatusAndCreateDateBefore(
//                AssignmentStatus.TODAY, now.minusDays(2));
//        todayAssignments.forEach(assignment -> assignment.updateAssignmentStatus(AssignmentStatus.ING));
//
//        // ING인 상태 && 7일이 지난 과제
//        List<Assignment> ingAssignments = assignmentRepository.findByAssignmentStatusAndCreateDateBefore(
//                AssignmentStatus.ING, now.minusDays(7));
//        ingAssignments.forEach(assignment -> assignment.updateAssignmentStatus(AssignmentStatus.DONE));
//
//        // 상태 변경된 엔티티 저장
//        assignmentRepository.saveAll(todayAssignments);
//        assignmentRepository.saveAll(ingAssignments);
//    }

    @Transactional
    public Assignment addAssignment(TrackType trackType, String title, String subTitle, String description, LocalDate dueDate) {
        Assignment assignment = new Assignment(trackType, title, subTitle, description, dueDate);
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public Assignment updateAssignment(Long id, String title, String subTitle, String description, LocalDate dueDate) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        String newTitle = (title != null && !title.isEmpty() ? title : assignment.getTitle());
        String newSubTitle = (subTitle != null && !subTitle.isEmpty() ? subTitle : assignment.getSubTitle());
        String newDescription = (description != null && !description.isEmpty() ? description : assignment.getDescription());
        LocalDate newDueDate = (dueDate != null ? dueDate : assignment.getDueDate());
        assignment.update(newTitle, newSubTitle, newDescription, newDueDate);
        return assignment;
    }

    @Transactional
    public void updateAssignmentStatusToDone(Long assignmentId, AssignmentStatus assignmentStatus) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(InvalidIdException::new);

        assignment.updateAssignmentStatus(assignmentStatus);
        assignmentRepository.save(assignment);
    }

    public Map<String, Object> getAssignmentsAndCountByTrackAndStatus(TrackType track, AssignmentStatus assignmentStatus) {
        List<Assignment> assignments = assignmentRepository.findByTrackAndAssignmentStatus(track, assignmentStatus);
        int assignmentCount = assignments.size();

        Map<String, Object> result = new HashMap<>();
        result.put("count", assignmentCount);
        result.put("assignments", assignments);

        return result;
    }
    public List<Assignment> findAssignmentsByAssignmentStatusAndTrack(AssignmentStatus status, TrackType track) {
        return assignmentRepository.findAssignmentsByAssignmentStatusAndTrack(status, track);
    }

    public Assignment findAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }
    public  List<Assignment> findAssignmentsByTrack(TrackType trackType) {
        return assignmentRepository.findAssignmentsByTrack(trackType);
    }
    public List<Assignment> findByTrackOrderByIdDesc(TrackType track) {
        return assignmentRepository.findByTrackOrderByIdDesc(track);
    }
    public int countByAssignmentStatusAndTrack(AssignmentStatus assignmentStatus, TrackType track) {
        return assignmentRepository.countByAssignmentStatusAndTrack(assignmentStatus, track);
    }

    public int countByTrack(TrackType trackType) {
        return assignmentRepository.countByTrack(trackType);
    }

    @Transactional
    public void deleteAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        assignmentRepository.delete(assignment);
    }

    @Transactional
    public void deleteAssignmentsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new InvalidListIdException();
        }
        for (Long id : ids) {
            Assignment assignment = assignmentRepository.findById(id)
                    .orElseThrow(InvalidIdException::new);
            assignmentRepository.delete(assignment);
        }
    }
}
