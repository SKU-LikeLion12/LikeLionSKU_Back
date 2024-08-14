package likelion.sku_sku.service;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    @Transactional
    public Assignment addAssignment(TrackType trackType, String title, String description) {
        Assignment assignment = new Assignment(trackType, title, description);
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public Assignment updateAssignment(Long id, String title, String description) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        String newTitle = (title != null && !title.isEmpty() ? title : assignment.getTitle());
        String newDescription = (description != null && !description.isEmpty() ? description : assignment.getDescription());
        assignment.update(newTitle, newDescription);
        return assignment;
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

    public int countByAssignmentStatusAndTrack(AssignmentStatus assignmentStatus, TrackType track) {
        return assignmentRepository.countByAssignmentStatusAndTrack(assignmentStatus, track);
    }

    @Transactional
    public void deleteAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        assignmentRepository.delete(assignment);
    }
}
