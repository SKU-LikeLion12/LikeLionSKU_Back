package likelion.sku_sku.service;

import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }
    Optional<Feedback> findFeedbackBySubmitAssignment(SubmitAssignment submitAssignment) {
        return feedbackRepository.findFeedbackBySubmitAssignment(submitAssignment);
    }

    Optional<Feedback> findFeedbackBySubmitAssignment_Id(Long submitAssignmentId) {
        return feedbackRepository.findFeedbackBySubmitAssignment_Id(submitAssignmentId);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }

    @Transactional
    public void deleteFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        feedbackRepository.delete(feedback);
    }
}
