package likelion.sku_sku.service;

import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Transactional
    public void deleteFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        feedbackRepository.delete(feedback);
    }

    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    Optional<Feedback> findFeedbackBySubmitAssignment(SubmitAssignment submitAssignment) {
        return feedbackRepository.findFeedbackBySubmitAssignment(submitAssignment);
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }
}
