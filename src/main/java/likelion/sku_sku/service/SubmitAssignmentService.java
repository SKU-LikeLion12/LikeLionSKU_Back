package likelion.sku_sku.service;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.SubmitAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmitAssignmentService {
    private final SubmitAssignmentRepository submitAssignmentRepository;
    @Transactional
    public SubmitAssignment submitAssignment(TrackType track, Assignment assignment, String writer, SubmitStatus submitStatus, PassNonePass passNonePass) {
        SubmitAssignment submitAssignment = new SubmitAssignment(track, assignment, writer, submitStatus, passNonePass);
        return submitAssignmentRepository.save(submitAssignment);
    }

    public List<SubmitAssignment> getAllSubmissions() {
        return submitAssignmentRepository.findAll();
    }

    public SubmitAssignment getSubmissionById(Long id) {
        return submitAssignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }

    @Transactional
    public void deleteSubmission(Long id) {
        SubmitAssignment submitAssignment = submitAssignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        submitAssignmentRepository.delete(submitAssignment);
    }
}
