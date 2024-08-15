package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static likelion.sku_sku.dto.JoinLectureFilesDTO.CreateJoinLectureFilesRequest;

public class LectureDTO {

    @Data
    public static class createLectureRequest {
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType trackType;
        @Schema(description = "강의자료 제목", example = "백엔드 3주차")
        private String title;
        @Schema(description = "강의자료 파일", example = "파일 넣으셔")
        private List<MultipartFile> files;
    }

    @Data
    public static class updateLectureRequest {
        @Schema(description = "강의자료 id", example = "1")
        private Long id;
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType trackType;
        @Schema(description = "강의자료 제목", example = "백엔드 3주차")
        private String title;
        @Schema(description = "강의자료 파일", example = "파일 넣으셔")
        private List<MultipartFile> files;
    }

    @Data
    @AllArgsConstructor
    public static class ResponseLecture {
        @Schema(description = "강의자료 id", example = "1")
        private Long id;
        @Schema(description = "트랙", example = "BACKEND or FRONTEND or PM_DESIGN")
        private TrackType trackType;
        @Schema(description = "강의자료 제목", example = "백엔드 3주차")
        private String title;
        @Schema(description = "강의자료 작성자", example = "한민규")
        private String writer;
        @Schema(description = "강의자료 조회수", example = "21")
        private int views;
        @Schema(description = "강의자료 작성 시간", example = "YYYY-MM-DD")
        private LocalDate createDate;
        @Schema(description = "강의자료 파일", example = "")
        private List<CreateJoinLectureFilesRequest> joinLectureFiles;
    }

    @Data
    @AllArgsConstructor
    public static class ResponseLectureWithoutFiles {
        @Schema(description = "강의자료 id", example = "1")
        private Long id;
        @Schema(description = "강의자료 제목", example = "백엔드 3주차")
        private String title;
        @Schema(description = "강의자료 작성자", example = "한민규")
        private String writer;
        @Schema(description = "강의자료 조회수", example = "21")
        private int views;
        @Schema(description = "강의자료 작성 시간", example = "YYYY-MM-DD")
        private LocalDate createDate;
    }
}
