package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Data;

import java.util.List;

import static likelion.sku_sku.dto.SubmitAssignmentDTO.*;

public class AssignmentDTO {

    @Data
    public static class createAssignmentRequest {
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType trackType;
        @Schema(description = "과제 안내물 제목", example = "백엔드 과제 안내")
        private String title;
        @Schema(description = "과제 안내물 서브제목", example = "[발표1]")
        private String subTitle;
        @Schema(description = "과제 안내물 설명", example = "Article domain 작성해오기")
        private String description;
    }

    @Data
    public static class updateAssignmentRequest {
        @Schema(description = "과제 안내물 id", example = "1")
        private Long id;
        @Schema(description = "과제 안내물 제목", example = "백엔드 과제 안내 [3주차]")
        private String title;
        @Schema(description = "과제 안내물 서브제목", example = "[발표1]")
        private String subTitle;
        @Schema(description = "과제 안내물 설명", example = "Article domain 작성해오기")
        private String description;
    }

    @Data
    public static class AssignmentIds {
        private List<Long> id;
    }

    @Data
    public static class AssignmentStatusDTO {
        private Long assignmentId;
        private TrackType track;
        private AssignmentStatus assignmentStatus;
        private String title;
        private String subTitle;
        private String description;
        private SubmitAssignmentWithoutDTO submitAssignmentWithoutDTO;

        public AssignmentStatusDTO(Assignment assignment, AssignmentStatus assignmentStatus, SubmitAssignmentWithoutDTO submitAssignmentWithoutDTO) {
            this.assignmentId = assignment.getId();
            this.track = assignment.getTrack();
            this.title = assignment.getTitle();
            this.subTitle = assignment.getSubTitle();
            this.description = assignment.getDescription();
            this.assignmentStatus = assignmentStatus;
            this.submitAssignmentWithoutDTO = submitAssignmentWithoutDTO;
        }
    }

    @Data
    public static class FindTrackStatus {
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType track;
        @Schema(description = "과제 안내물 진행 상태", example = "TODAY or ING or DONE")
        private AssignmentStatus status;
    }

    @Data
    public static class AssignmentAllDTO {
        @Schema(description = "과제 안내물 id", example = "1")
        private Long id;
        @Schema(description = "과제 안내물 제목", example = "백엔드 과제 안내 [3주차]")
        private String title;
        @Schema(description = "과제 안내물 서브제목", example = "[발표1]")
        private String subTitle;
        @Schema(description = "과제 안내물 설명", example = "Article domain 작성해오기")
        private String description;
        @Schema(description = "해당 과제 안내물에 대해 제출한 과제", example = "")
        private SubmitAssignmentAllDTO submitAssignmentAllDTO;

        public AssignmentAllDTO(Assignment assignment, SubmitAssignmentAllDTO submitAssignmentAllDTO) {
            this.id = assignment.getId();
            this.title = assignment.getTitle();
            this.subTitle = assignment.getSubTitle();
            this.description = assignment.getDescription();
            this.submitAssignmentAllDTO = submitAssignmentAllDTO;
        }
    }

//    @Data
//    public static class AssignmentWithoutDTO {
//        @Schema(description = "과제 안내물 id", example = "1")
//        private Long id;
//        @Schema(description = "과제 안내물 제목", example = "백엔드 과제 안내 [3주차]")
//        private String title;
//        @Schema(description = "과제 안내물 서브제목", example = "[발표1]")
//        private String subTitle;
//        @Schema(description = "과제 안내물 설명", example = "Article domain 작성해오기")
//        private String description;
//        @Schema(description = "해당 과제 안내물에 대해 제출한 과제", example = "")
//        private SubmitAssignmentWithoutDTO submitAssignmentWithoutDTO;
//
//        public AssignmentWithoutDTO(Assignment assignment, SubmitAssignmentWithoutDTO submitAssignmentWithoutDTO) {
//            this.id = assignment.getId();
//            this.title = assignment.getTitle();
//            this.subTitle = assignment.getSubTitle();
//            this.description = assignment.getDescription();
//            this.submitAssignmentWithoutDTO = submitAssignmentWithoutDTO;
//        }
//    }

    @Data
    public static class AssignmentAll {
        @Schema(description = "과제 안내물 id", example = "1")
        private Long id;
        @Schema(description = "과제 안내물 제목", example = "백엔드 과제 안내 [3주차]")
        private String title;
        @Schema(description = "과제 안내물 서브제목", example = "[발표1]")
        private String subTitle;
        @Schema(description = "과제 안내물 설명", example = "Article domain 작성해오기")
        private String description;
        @Schema(description = "해당 과제 안내물에 대해 제출한 과제", example = "")
        private AssignSubmitFeed assignSubmitFeed;

        public AssignmentAll(Assignment assignment, AssignSubmitFeed assignSubmitFeed) {
            this.id = assignment.getId();
            this.title = assignment.getTitle();
            this.subTitle = assignment.getSubTitle();
            this.description = assignment.getDescription();
            this.assignSubmitFeed = assignSubmitFeed;
        }
    }
}
