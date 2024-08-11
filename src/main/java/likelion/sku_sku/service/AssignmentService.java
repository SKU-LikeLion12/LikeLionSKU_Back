package likelion.sku_sku.service;

import likelion.sku_sku.domain.SubmitAssignment;
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
    public SubmitAssignment addAssignment(String title) {
        SubmitAssignment assignment = new SubmitAssignment(title);
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public SubmitAssignment updateAssignment(Long id, String title) {
        SubmitAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        String newTitle = (title != null && !title.isEmpty() ? title : assignment.getTitle());
        assignment.update(newTitle);
        return assignmentRepository.save(assignment);
    }
    @Transactional
    public void deleteAssignmentById(Long id) {
        SubmitAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        assignmentRepository.delete(assignment);
    }
}
