package likelion.sku_sku.dto;

import likelion.sku_sku.domain.enums.PassNonePass;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
