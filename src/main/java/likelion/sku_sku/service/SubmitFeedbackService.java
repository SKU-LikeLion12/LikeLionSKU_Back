package likelion.sku_sku.service;

import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.exception.AlreadyFeedbackException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmitFeedbackService {
    private final SubmitAssignmentService submitAssignmentService;
    private final FeedbackService feedbackService;

    // @PostMapping("/admin/feedback/add")
    @Transactional
    public Map<String, Object> addFeedbackPassStatus(Long submitAssignmentId, PassNonePass passNonePass, String content) {
        SubmitAssignment submitAssignment = submitAssignmentService.findSubmitAssignmentById(submitAssignmentId);
        Map<String, Object> response = new HashMap<>();
        if (passNonePass == PassNonePass.PASS) {
            submitAssignment.updatePassNonePass(passNonePass);
            submitAssignment.updateStatusAssignment(AssignmentStatus.DONE);
            response.put("passStatus", "PASS로 변경");
        }

        if (passNonePass == PassNonePass.FAIL) {
            submitAssignment.updatePassNonePass(passNonePass);
            submitAssignment.updateStatusAssignment(AssignmentStatus.ING);
            response.put("passStatus", "FAIL로 변경");
        }

        if (content != null && !content.isEmpty()) {
            if (feedbackService.findFeedbackBySubmitAssignment(submitAssignment).isPresent()) {
                throw new AlreadyFeedbackException();
            }
            Feedback feedback = new Feedback(submitAssignment, content);
            feedbackService.saveFeedback(feedback);
            response.put("feedback", feedback);
        }

        return response;
    }

    // @PutMapping("/admin/feedback/update")
    @Transactional
    public Map<String, Object> updateFeedbackPassStatus(Long feedbackId, PassNonePass passNonePass, String content) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);

        Map<String, Object> response = new HashMap<>();

        if (content != null && !content.isEmpty()) {
            feedback.update(content);
            response.put("feedback", feedback);
            response.put("message", "Feedback 내용 변경");
        }

        if (passNonePass != null) {
            SubmitAssignment submitAssignment = feedback.getSubmitAssignment();
            submitAssignment.updatePassNonePass(passNonePass);
            if (passNonePass == PassNonePass.PASS) {
                submitAssignment.updateStatusAssignment(AssignmentStatus.DONE);
            } else {
                submitAssignment.updateStatusAssignment(AssignmentStatus.ING);
            }
            response.put("passStatus", passNonePass == PassNonePass.PASS ? "PASS로 변경" : "FAIL로 변경");
        }

        feedbackService.saveFeedback(feedback);
        return response;
    }
}
