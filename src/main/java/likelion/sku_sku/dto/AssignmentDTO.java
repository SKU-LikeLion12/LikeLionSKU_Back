package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public class AssignmentDTO {

    @Data
    public static class createAssignmentRequest {
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType trackType;
        @Schema(description = "과제 제목", example = "백엔드 과제 안내 [3주차]")
        private String title;
        @Schema(description = "과제 설명", example = "Article domain 작성해오기")
        private String description;
    }

    @Data
    public static class updateAssignmentRequest {
        @Schema(description = "강의 id", example = "1")
        private Long id;
        @Schema(description = "과제 제목", example = "백엔드 과제 안내 [3주차]")
        private String title;
        @Schema(description = "과제 설명", example = "Article domain 작성해오기")
        private String description;
    }

    @Data
    public static class FindTrackStatus {
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType track;
        @Schema(description = "과제 진행 상태", example = "TODAY or ING or DONE")
        private AssignmentStatus status;
    }



}
