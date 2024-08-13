package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.JoinAssignmentFiles;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static likelion.sku_sku.dto.AssignmentDTO.*;

public class SubmitAssignmentDTO {

    @Data
    public static class CreateSubmitRequest {
        private Long assignmentId;
        private List<MultipartFile> files;
    }

    @Data
    public static class UpdateSubmitRequest {
        private Long submitAssignmentId;
        private List<MultipartFile> files;
    }

    @Data
    public static class DecidePassStatusRequest {
        private Long submitAssignmentId;
    }

    @Data
    public static class ResponseAssignmentDetails {
        private String writer;
        private int submittedTodayCount;
        private int todayCount;
        private int submittedIngCount;
        private int ingCount;
        private int doneCount;
        private Map<String, List<AssignmentAllDTO>> assignments;

        public ResponseAssignmentDetails(String writer, int submittedTodayCount, int todayCount, int submittedIngCount, int ingCount, int doneCount, Map<String, List<AssignmentAllDTO>> assignments) {
            this.writer = writer;
            this.submittedTodayCount = submittedTodayCount;
            this.todayCount = todayCount;
            this.submittedIngCount = submittedIngCount;
            this.ingCount = ingCount;
            this.doneCount = doneCount;
            this.assignments = assignments;
        }
    }

    @Data
    @AllArgsConstructor
    public static class ResponseAssignmentCount {
        private String writer;
        private int submittedTodayCount;
        private int todayCount;

        private int submittedIngCount;
        private int ingCount;

        private int doneCount;
    }

    @Data
    @AllArgsConstructor
    public static class ResponseSubmit {
        @Schema(description = "과제 id", example = "1")
        private Long id;
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType trackType;
        @Schema(description = "강의 안내물 id", example = "1")
        private Long assignmentId;
        @Schema(description = "과제 작성자", example = "한민규")
        private String writer;
        @Schema(description = "과제 제출 상태", example = "SUBMITTED or UNSUBMITTED")
        private SubmitStatus submitStatus;
        @Schema(description = "과제 통과 상태", example = "PASS or FAIL")
        private PassNonePass passNonePass;
    }

    @Data
    public static class SubmitAssignmentDetails {
        private SubmitAssignment submitAssignment;
        private List<JoinAssignmentFiles> files;

        public SubmitAssignmentDetails(SubmitAssignment submitAssignment, List<JoinAssignmentFiles> files) {
            this.submitAssignment = submitAssignment;
            this.files = files;
        }
    }

    @Data
    public static class SubmitAssignmentAllDTO {  // 리팩토링된 클래스 이름
        private Long id;
        private TrackType track;
        private String writer;
        private SubmitStatus submitStatus;
        private PassNonePass passNonePass;
        private LocalDateTime createDate;
        private List<JoinAssignmentFiles> files;  // files를 여기 포함

        public SubmitAssignmentAllDTO(SubmitAssignment submitAssignment, List<JoinAssignmentFiles> files) {
            this.id = submitAssignment.getId();
            this.track = submitAssignment.getTrack();
            this.writer = submitAssignment.getWriter();
            this.submitStatus = submitAssignment.getSubmitStatus();
            this.passNonePass = submitAssignment.getPassNonePass();
            this.createDate = submitAssignment.getCreateDate();
            this.files = files;
        }
    }
}
