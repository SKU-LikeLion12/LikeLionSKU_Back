package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.JoinAssignmentFiles;
import lombok.Data;

public class JoinAssignmentFilesDTO {

    @Data
    public static class ResponseJoinAss {
        private Long submitAssignmentId;
        @Schema(description = "파일 이름", example = "Spring.pdf")
        private String fileName;
        @Schema(description = "파일 유형", example = "application/pdf")
        private String fileType;
        @Schema(description = "파일 사이즈", example = "65362")
        private long size;
        @Schema(description = "파일", example = "base64 인코딩 값")
        private String file; // base64로 인코딩된 파일 데이터
        public ResponseJoinAss(JoinAssignmentFiles joinAssignmentFiles) {
            this.submitAssignmentId = joinAssignmentFiles.getId();
            this.fileName = joinAssignmentFiles.getFileName();
            this.fileType = joinAssignmentFiles.getFileType();
            this.size = joinAssignmentFiles.getSize();
            this.file = joinAssignmentFiles.arrayToFile();
        }
    }
}
