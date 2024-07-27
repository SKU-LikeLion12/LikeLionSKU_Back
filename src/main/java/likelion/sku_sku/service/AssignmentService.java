package likelion.sku_sku.service;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    @Transactional
    public Assignment addAssignment(String title) {
        Assignment assignment = new Assignment(title);
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public Assignment updateAssignment(Long id, String title) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        String newTitle = (title != null && !title.isEmpty() ? title : assignment.getTitle());
        assignment.update(newTitle);
        return assignmentRepository.save(assignment);
    }
    @Transactional
    public void deleteAssignmentById(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        assignmentRepository.delete(assignment);
    }
}
