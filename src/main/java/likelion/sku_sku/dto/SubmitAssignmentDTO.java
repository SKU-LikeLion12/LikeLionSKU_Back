package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.JoinAssignmentFiles;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
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
import static likelion.sku_sku.dto.AssignmentDTO.AssignmentStatusDTO;
import static likelion.sku_sku.dto.FeedbackDTO.ResponseFeedback;

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
        @Schema(description = "아기사자 이름", example = "한민규")
        private String writer;
        @Schema(description = "해당 트랙 (구)오늘의 과제 수", example = "3")
        private int unsubmittedCount;
        @Schema(description = "해당 트랙 (구)진행중인 과제 개수", example = "3")
        private int submittedCount;
        @Schema(description = "해당 트랙 통과된 과제 개수", example = "3")
        private int passCount;
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
    public static class SubmitAssignmentWithoutDTO {
        @Schema(description = "제출한 과제 id", example = "1")
        private Long submitAssignmentId;
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType track;
        @Schema(description = "제출한 과제 작성자", example = "한민규")
        private String writer;
        @Schema(description = "과제 제출 상태", example = "SUBMITTED or UNSUBMITTED")
        private SubmitStatus submitStatus;
        @Schema(description = "제출한 과제 통과 상태", example = "PASS or FAIL")
        private PassNonePass passNonePass;
        @Schema(description = "제출한 과제에 대한 피드백", example = "")
        private ResponseFeedback responseFeedback;
        @Schema(description = "제출한 과제 파일들", example = "")
        private List<JoinAssignmentFiles> files;


        public SubmitAssignmentWithoutDTO(SubmitAssignment submitAssignment, List<JoinAssignmentFiles> files, ResponseFeedback responseFeedback) {
            this.submitAssignmentId = submitAssignment.getId();
            this.track = submitAssignment.getTrack();
            this.writer = submitAssignment.getWriter();
            this.submitStatus = submitAssignment.getSubmitStatus();
            this.passNonePass = submitAssignment.getPassNonePass();
            this.responseFeedback = responseFeedback;
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
        private ResponseFeedback feedbacks;

        public AssignSubmitFeed(SubmitAssignment submitAssignment,
                                List<JoinAssignmentFilesDTO.ResponseJoinAss> files,
                                ResponseFeedback feedbacks) {
            this.id = submitAssignment.getId();
            this.track = submitAssignment.getTrack();
            this.writer = submitAssignment.getWriter();
            this.submitStatus = submitAssignment.getSubmitStatus();
            this.passNonePass = submitAssignment.getPassNonePass();
            this.createDate = submitAssignment.getCreateDate();
            this.files = files;
            this.feedbacks = feedbacks;
        }
    }

    @Data
    @AllArgsConstructor
    public static class ResponseAssignmentSummary {
        @Schema(description = "해당 트랙의 총 과제 수", example = "한민규")
        private int totalAssignmentsByTrack;
        @Schema(description = "해당 트랙의 아기사자 과제의 디테일 개수", example = "[\n" +
                "        {\n" +
                "            \"writer\": \"한민규\",\n" +
                "            \"unsubmittedCount\": 2,\n" +
                "            \"submittedCount\": 2,\n" +
                "            \"passCount\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"writer\": \"김영현\",\n" +
                "            \"unsubmittedCount\": 1,\n" +
                "            \"submittedCount\": 3,\n" +
                "            \"passCount\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"writer\": \"고창준\",\n" +
                "            \"unsubmittedCount\": 3,\n" +
                "            \"submittedCount\": 1,\n" +
                "            \"passCount\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"writer\": \"신민서\",\n" +
                "            \"unsubmittedCount\": 1,\n" +
                "            \"submittedCount\": 3,\n" +
                "            \"passCount\": 0\n" +
                "        }\n" +
                "    ]\n")
        private List<ResponseAssignmentCount> assignmentCounts;
    }


    @Data
    public static class ResponseAssignmentDetail {
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
        private Map<AssignmentStatus, List<AssignmentAllDTO>> assignments;

        public ResponseAssignmentDetail(String writer, int submittedTodayCount, int todayCount, int submittedIngCount, int ingCount, int doneCount, Map<AssignmentStatus, List<AssignmentAllDTO>> assignments) {
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
    public static class AssignmentStatusGroupedDTO {
        @Schema(description = "제출한 과제 작성자", example = "한민규")
        private String writer;

        @Schema(description = "아직 제출하지 않은 과제 안내물 개수", example = "1")
        private int todayCount;

        @Schema(description = "제출하지 않은 과제 안내물 목록", example = "[{\"assignmentId\": 219, \"track\": \"BACKEND\", \"assignmentStatus\": \"ING\", \"title\": \"아니\", \"subTitle\": \"정말\", \"description\": \"테스트3\", \"submitAssignmentWithoutDTO\": null}]")
        private List<AssignmentStatusDTO> today;

        @Schema(description = "제출한 과제 안내물 개수", example = "1")
        private int ingCount;

        @Schema(description = "제출한 과제 안내물 목록", example = "[{\"assignmentId\": 219, \"track\": \"BACKEND\", \"assignmentStatus\": \"ING\", \"title\": \"아니\", \"subTitle\": \"정말\", \"description\": \"테스트3\", \"dueDate\": \"2024-08-25\", \"submitAssignmentWithoutDTO\": {\"submitAssignmentId\": 452, \"track\": \"BACKEND\", \"writer\": \"한민규\", \"submitStatus\": \"SUBMITTED or UNSUBMITTED\", \"passNonePass\": \"PASS or FAIL\", \"responseFeedback\": {\"feedBackId\": 302, \"content\": \"이게 최선이야?\"}, \"files\": [{\"id\": 452, \"fileName\": \"swagger.pdf\", \"fileType\": \"application/pdf\", \"size\": 199922, \"file\": \"base64 fileString\"}]}}]")
        private List<AssignmentStatusDTO> ing;

        @Schema(description = "끝난 과제 안내물 개수", example = "1")
        private int doneCount;

        @Schema(description = "끝난 과제 안내물 목록", example = "[{\"assignmentId\": 219, \"track\": \"BACKEND\", \"assignmentStatus\": \"ING\", \"title\": \"아니\", \"subTitle\": \"정말\", \"description\": \"테스트3\", \"dueDate\": \"2024-08-25\", \"submitAssignmentWithoutDTO\": {\"submitAssignmentId\": 452, \"track\": \"BACKEND\", \"writer\": \"한민규\", \"submitStatus\": \"SUBMITTED or UNSUBMITTED\", \"passNonePass\": \"PASS or FAIL\", \"responseFeedback\": {\"feedBackId\": 302, \"content\": \"이게 최선이야?\"}, \"files\": [{\"id\": 452, \"fileName\": \"swagger.pdf\", \"fileType\": \"application/pdf\", \"size\": 199922, \"file\": \"base64 fileString\"}]}}]")
        private List<AssignmentStatusDTO> done;
    }

    @Data
    @AllArgsConstructor
    public static class AssignmentStatusGrouped {
        @Schema(description = "제출한 과제 작성자", example = "한민규")
        private String writer;

        @Schema(description = "진행중인 과제 개수", example = "1")
        private int submittedCount;

        @Schema(description = "통과된 과제 개수", example = "1")
        private int passCount;

        @Schema(description = "오늘의 과제 개수 and 오늘의 과제 관리 개수", example = "1")
        private int todayCount;

        @Schema(description = "제출하지 않은 과제 안내물 목록", example = "[{\"assignmentId\": 219, \"track\": \"BACKEND\", \"assignmentStatus\": \"ING\", \"title\": \"아니\", \"subTitle\": \"정말\", \"description\": \"테스트3\", \"dueDate\": \"2024-08-25\", \"submitAssignmentWithoutDTO\": null}]")
        private List<AssignmentStatusDTO> today;

        @Schema(description = "진행중인 과제 관리 개수", example = "1")
        private int ingCount;

        @Schema(description = "제출한 과제 안내물 목록", example = "[{\"assignmentId\": 219, \"track\": \"BACKEND\", \"assignmentStatus\": \"ING\", \"title\": \"아니\", \"subTitle\": \"정말\", \"description\": \"테스트3\", \"dueDate\": \"2024-08-25\", \"submitAssignmentWithoutDTO\": {\"submitAssignmentId\": 452, \"track\": \"BACKEND\", \"writer\": \"한민규\", \"submitStatus\": \"SUBMITTED or UNSUBMITTED\", \"passNonePass\": \"PASS or FAIL\", \"responseFeedback\": {\"feedBackId\": 302, \"content\": \"이게 최선이야?\"}, \"files\": [{\"id\": 452, \"fileName\": \"swagger.pdf\", \"fileType\": \"application/pdf\", \"size\": 199922, \"file\": \"base64 fileString\"}]}}]")
        private List<AssignmentStatusDTO> ing;
    }
}
