package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType trackType;
        @Schema(description = "강의 제목", example = "백엔드 3주차")
        private String title;
        @Schema(description = "강의 파일", example = "파일 넣으셔")
        private List<MultipartFile> files;
    }

    @Data
    @AllArgsConstructor
    public static class ResponseAssignment {
        @Schema(description = "강의 id", example = "1")
        private Long id;
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType trackType;
        @Schema(description = "강의 제목", example = "백엔드 3주차")
        private String title;
        @Schema(description = "강의 작성자", example = "한민규")
        private String writer;
        @Schema(description = "강의 조회수", example = "21")
        private int views;
        @Schema(description = "강의 작성 시간", example = "YYYY-MM-DD")
        private LocalDate createDate;
        @Schema(description = "강의 파일", example = """
                                                    {
                                                        "id": 1,
                                                        "fileName": "Spring.pdf",
                                                        "fileType": "application/pdf",
                                                        "size": 65362,
                                                        "file": "base64 인코딩 값"
                                                    }
                                                    """)
        private List<JoinLectureFilesDTO.CreateJoinLectureFilesRequest> joinLectureFiles;
    }

}
