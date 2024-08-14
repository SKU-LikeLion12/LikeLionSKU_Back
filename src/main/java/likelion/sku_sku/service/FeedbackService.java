package likelion.sku_sku.service;

import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final SubmitAssignmentService submitAssignmentService;

    @Transactional
    public Feedback addFeedback(Long submitAssignmentId, String content) {
        SubmitAssignment submitAssignment = submitAssignmentService.findSubmitAssignmentById(submitAssignmentId);
        Feedback feedback = new Feedback(submitAssignment, content);
        return feedbackRepository.save(feedback);
    }

    @Transactional
    public Feedback updateFeedback(Long feedBackId, String content) {
        Feedback feedback = feedbackRepository.findById(feedBackId)
                .orElseThrow(InvalidIdException::new);
        feedback.update(content != null && !content.isEmpty() ? content : feedback.getContent());
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }

    public List<Feedback> findFeedbackBySubmitAssignment(SubmitAssignment submitAssignment) {
        return feedbackRepository.findFeedbackBySubmitAssignment(submitAssignment);
    }

    @Transactional
    public void deleteFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        feedbackRepository.delete(feedback);
    }
}
