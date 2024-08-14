package likelion.sku_sku.service;

import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final SubmitAssignmentService submitAssignmentService;

    @Transactional
    public Map<String, Object> addFeedbackPassStatus(Long submitAssignmentId, PassNonePass passNonePass, String content) {
        SubmitAssignment submitAssignment = submitAssignmentService.findSubmitAssignmentById(submitAssignmentId);
        Map<String, Object> response = new HashMap<>();
        System.out.println(passNonePass);
        if (passNonePass == PassNonePass.PASS) {
            submitAssignment.update(passNonePass);
            response.put("passStatus", "PASS로 변경하였습니다.");
        }
        if (passNonePass == PassNonePass.FAIL) {
            submitAssignment.update(passNonePass);
            response.put("passStatus", "FAIL로 변경하였습니다.");
        }
        if (content != null && !content.isEmpty()) {
            Feedback feedback = new Feedback(submitAssignment, content);
            feedbackRepository.save(feedback);
            response.put("feedback", feedback);
        }
        return response;
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
