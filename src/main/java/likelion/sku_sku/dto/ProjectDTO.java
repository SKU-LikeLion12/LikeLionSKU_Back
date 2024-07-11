package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class ProjectDTO {

    // Request
    @Data
    public static class ProjectCreateRequest {
        @Schema(description = "프로젝트 제목", example = "스쿠스쿠")
        private String title;
        @Schema(description = "프로젝트 부제목", example = "LikeLion sku 공식페이지")
        private String subTitle;

        @Schema(description = "프로젝트 사진", example = "")
        private MultipartFile image;
    }

    @Data
    public static class ProjectUpdateRequest{
        @Schema(description = "프로젝트 아이디", example = "1")
        private Long id;

        @NotEmpty
        @Schema(description = "프로젝트 제목", example = "스쿠스쿠")
        private String title;

        @NotEmpty
        @Schema(description = "프로젝트 부제목", example = "LikeLion sku 공식페이지")
        private String subTitle;

        @Nullable
        @Schema(description = "프로젝트 이미지(생략가능)", example = "")
        private MultipartFile image;
    }
    @Data
    public static class TitleRequest {
        @Schema(description = "프로젝트 제목", example = "스쿠스쿠")
        private String title;
    }
// sonsworld@hanmail.net

    // Response
    @Data
    @AllArgsConstructor
    public static class ResponseProjectUpdate {
        @Schema(description = "프로젝트 제목", example = "스쿠스쿠")
        private String title;

        @Schema(description = "프로젝트 부제목", example = "LikeLion sku 공식페이지")
        private String subTitle;

        @Schema(description = "프로젝트 사진", example = "")
        private String image;
    }
}
