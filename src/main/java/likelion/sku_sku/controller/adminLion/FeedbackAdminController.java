package likelion.sku_sku.controller.adminLion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static likelion.sku_sku.dto.FeedbackDTO.FeedBackPassStatus;
import static likelion.sku_sku.dto.FeedbackDTO.UpdateFeedbackRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/feedback")
@Tag(name = "관리자 기능: 피드백 관련")
public class FeedbackAdminController {
    private final FeedbackService feedbackService;

    @Operation(summary = "(민규) 제출한 과제 상태 변경 또는 피드백 추가", description = "Headers에 Bearer token 필요, body에 json 형태로 과제 안내물 id 필요, 통과 상태와 피드백 내용은 추가할 때만 넣으면 됨",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<?> setPassStatusFeedback(@RequestBody FeedBackPassStatus request) {
        Map<String, Object> response = feedbackService.addFeedbackPassStatus
                (request.getSubmitAssignmentId(), request.getPassNonePass(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "(민규) 피드백 수정", description = "Headers에 Bearer token 필요, body에 json 형태로 피드백 id, 피드백 내용 필요",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공"),
                    @ApiResponse(responseCode = "404", description = "그 id에 해당하는 값 없")})
    @PutMapping("/update")
    public ResponseEntity<?> updateFeedback(@RequestBody UpdateFeedbackRequest request) {
        Feedback feedback = feedbackService.updateFeedback(request.getFeedBackId(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }
}
