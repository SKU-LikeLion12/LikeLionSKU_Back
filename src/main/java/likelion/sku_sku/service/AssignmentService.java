package likelion.sku_sku.service;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Assignment> getAssignmentsByTrackAndStatus(TrackType track, AssignmentStatus assignmentStatus) {
        return assignmentRepository.findByTrackAndAssignmentStatus(track, assignmentStatus);
    }

    public List<Assignment> findAssignmentsByTrack(TrackType track) {
        return assignmentRepository.findByTrack(track);
    }

    public List<Assignment> findAssignmentsByStatus(AssignmentStatus status) {
        return assignmentRepository.findByAssignmentStatus(status);
    }

    public List<Assignment> findAllAssignment() {
        return assignmentRepository.findAll();
    }

    public Assignment findAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }

    public int countByAssignmentStatus(AssignmentStatus assignmentStatus) {
        return assignmentRepository.countByAssignmentStatus(assignmentStatus);
    }

    @Transactional
    public void deleteAssignment(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        assignmentRepository.delete(assignment);
    }



}
