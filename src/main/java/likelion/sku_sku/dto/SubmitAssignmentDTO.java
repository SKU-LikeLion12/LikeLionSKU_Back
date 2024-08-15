package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

import static likelion.sku_sku.dto.AssignmentDTO.AssignmentAllDTO;

public class SubmitAssignmentDTO {

    @Data
    public static class CreateSubmitRequest {
        @Schema(description = "과제 안내물 id", example = "1")
        private Long assignmentId;
        @Schema(description = "제출한 과제 파일들", example = "")
        private List<MultipartFile> files;
    }

    @Data
    public static class UpdateSubmitRequest {
        @Schema(description = "제출한 과제 id", example = "1")
        Long submitAssignmentId;
        @Schema(description = "제출한 과제 파일들", example = "")
        private List<MultipartFile> files;
    }

    @Data
    public static class DecidePassStatusRequest {
        @Schema(description = "제출한 과제 id", example = "1")
        private Long submitAssignmentId;
    }

    @Data
    public static class assignmentWriter {
        @Schema(description = "제출한 과제 id", example = "1")
        private Long assignmentId;
        private String writer;
    }

    @Data
    public static class WriterAndTrack {
        @Schema(description = "제출한 과제 작성자", example = "한민규")
        private String writer;
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType track;
    }

    @Data
    public static class ResponseAssignmentDetails {
        @Schema(description = "제출한 과제 작성자", example = "한민규")
        private String writer;
        @Schema(description = "제출한 오늘의 과제 개수", example = "1")
        private int submittedTodayCount;
        @Schema(description = "해당 트랙 오늘의 과제 종 개수", example = "3")
        private int todayCount;
        @Schema(description = "제출한 진행중인 과제 개수", example = "1")
        private int submittedIngCount;
        @Schema(description = "해당 트랙 진행중인 과제 종 개수", example = "3")
        private int ingCount;
        @Schema(description = "해당 트랙 완료된 과제 종 개수", example = "3")
        private int doneCount;
        @Schema(description = "과제 안내물", example = "")
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
        @Schema(description = "제출한 과제 작성자", example = "한민규")
        private String writer;
        @Schema(description = "제출한 오늘의 과제 개수", example = "1")
        private int submittedTodayCount;
        @Schema(description = "해당 트랙 오늘의 과제 종 개수", example = "3")
        private int todayCount;
        @Schema(description = "제출한 진행중인 과제 개수", example = "1")
        private int submittedIngCount;
        @Schema(description = "해당 트랙 진행중인 과제 종 개수", example = "3")
        private int ingCount;
        @Schema(description = "해당 트랙 완료된 과제 종 개수", example = "3")
        private int doneCount;
    }

    @Data
    @AllArgsConstructor
    public static class ResponseSubmit {
        @Schema(description = "제출한 과제 id", example = "1")
        private Long id;
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType trackType;
        @Schema(description = "강의 안내물 id", example = "1")
        private Long assignmentId;
        @Schema(description = "제출한 과제 작성자", example = "한민규")
        private String writer;
        @Schema(description = "과제 제출 상태", example = "SUBMITTED or UNSUBMITTED")
        private SubmitStatus submitStatus;
        @Schema(description = "과제 통과 상태", example = "PASS or FAIL")
        private PassNonePass passNonePass;
    }

    @Data
    public static class SubmitAssignmentAllDTO {
        @Schema(description = "제출한 과제 id", example = "1")
        private Long id;
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType track;
        @Schema(description = "제출한 과제 작성자", example = "한민규")
        private String writer;
        @Schema(description = "과제 제출 상태", example = "SUBMITTED or UNSUBMITTED")
        private SubmitStatus submitStatus;
        @Schema(description = "제출한 과제 통과 상태", example = "PASS or FAIL")
        private PassNonePass passNonePass;
        @Schema(description = "제출한 과제 제출 시간", example = "PASS or FAIL")
        private LocalDateTime createDate;
        @Schema(description = "제출한 과제 파일들", example = "")
        private List<JoinAssignmentFiles> files;

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

    @Data
    public static class AssignSubmitFeed {
        private Long id;
        private TrackType track;
        private String writer;
        private SubmitStatus submitStatus;
        private PassNonePass passNonePass;
        private LocalDateTime createDate;
        private List<JoinAssignmentFilesDTO.ResponseJoinAss> files;
        private List<FeedbackDTO.ResponseFeedback> feedbacks; // 피드백 리스트 추가

        public AssignSubmitFeed(SubmitAssignment submitAssignment,
                                List<JoinAssignmentFilesDTO.ResponseJoinAss> files,
                                List<FeedbackDTO.ResponseFeedback> feedbacks) {
            this.id = submitAssignment.getId();
            this.track = submitAssignment.getTrack();
            this.writer = submitAssignment.getWriter();
            this.submitStatus = submitAssignment.getSubmitStatus();
            this.passNonePass = submitAssignment.getPassNonePass();
            this.createDate = submitAssignment.getCreateDate();
            this.files = files;
            this.feedbacks = feedbacks; // 피드백 리스트 초기화
        }
    }

    @Data
    @AllArgsConstructor
    public static class ResponseAssignmentSummary {
        private int totalAssignmentsByTrack;
        private List<ResponseAssignmentCount> assignmentCounts;
    }

}
