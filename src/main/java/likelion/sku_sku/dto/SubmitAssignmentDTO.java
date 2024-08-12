package likelion.sku_sku.dto;

import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.PassNonePass;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
        private Map<String, List<SubmitAssignment>> assignments;

        public ResponseAssignmentDetails(String writer, int submittedTodayCount, int todayCount, int submittedIngCount, int ingCount, int doneCount, Map<String, List<SubmitAssignment>> assignments) {
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

}
