package likelion.sku_sku.dto;

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
}
