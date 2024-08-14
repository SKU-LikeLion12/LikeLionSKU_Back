package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.enums.PassNonePass;
import lombok.Data;

public class FeedbackDTO {

    @Data
    public static class CreateFeedbackRequest {
        @Schema(description = "제출한 과제 id", example = "1")
        private Long submitAssignmentId;

        @Schema(description = "피드백 내용", example = "하면 되지")
        private String content;
    }

    @Data
    public static class UpdateFeedbackRequest {
        @Schema(description = "피드백 id", example = "1")
        private Long feedBackId;

        @Schema(description = "변경할 피드백 내용", example = "뭐든지 하면 되지")
        private String content;
    }

    // Response
    @Data
    public static class ResponseFeedback {
        @Schema(description = "피드백 내용", example = "하면 되지")
        private String content;
        public ResponseFeedback(Feedback feedback) {
            this.content = feedback.getContent();
        }
    }

    @Data
    public static class FeedBackPassStatus {
        @Schema(description = "제출한 과제 id", example = "1")
        private Long submitAssignmentId;
        @Schema(description = "피드백 내용", example = "하면 되지")
        private String content;
        @Schema(description = "제출한 과제 통과 여부", example = "FAIL or PASS")
        private PassNonePass passNonePass;
    }

}
