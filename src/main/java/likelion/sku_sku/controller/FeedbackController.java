package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.dto.FeedbackDTO;
import likelion.sku_sku.dto.LectureDTO;
import likelion.sku_sku.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static likelion.sku_sku.dto.FeedbackDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary = "(민규) 강의자료 추가", description = "Headers에 Bearer token 필요, 강의의 trackType, title, 강의자료 필요, body에 form-data로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<?> createFeedback(@RequestBody CreateFeedbackRequest request) throws IOException {
        Feedback feedback = feedbackService.addFeedback(request.getSubmitAssignmentId(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }

    @Operation(summary = "(민규) 강의자료 수정", description = "Headers에 Bearer token 필요, 강의 ID와 수정할 정보를 body에 form-data로 포함시켜야 함",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 Id의 강의를 찾을 수 없음")})
    @PutMapping("/update")
    public ResponseEntity<?> updateFeedback(@RequestBody UpdateFeedbackRequest request) throws IOException {
        Feedback feedback = feedbackService.updateFeedback(request.getFeedBackId(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }
}
