package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.JoinLectureFiles;
import lombok.Data;

public class JoinLectureFilesDTO {
    @Data
    public static class CreateJoinLectureFilesRequest {
        @Schema(description = "강의 안내물 id", example = "1")
        private Long lectureId;
        @Schema(description = "강의자료 이름", example = "Spring.pdf")
        private String fileName;
        @Schema(description = "강의자료 유형", example = "application/pdf")
        private String fileType;
        @Schema(description = "강의자료 사이즈", example = "65362")
        private long size;
        @Schema(description = "강의자료", example = "base64 인코딩 값")
        private String file;

        public CreateJoinLectureFilesRequest(JoinLectureFiles joinLectureFiles) {
            this.lectureId = joinLectureFiles.getId();
            this.fileName = joinLectureFiles.getFileName();
            this.fileType = joinLectureFiles.getFileType();
            this.size = joinLectureFiles.getSize();
            this.file = joinLectureFiles.arrayToFile();
        }
    }
}
