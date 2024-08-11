package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.enums.RoleType;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.AllArgsConstructor;
import lombok.Data;

public class FeedbackDTO {

    @Data
    public static class CreateFeedbackRequest {
        @Schema(description = "과제 제출 id", example = "1")
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
    @AllArgsConstructor
    public static class ResponseFeedback {
        @Schema(description = "피드백 내용", example = "하면 되지")
        private String content;
    }
}
