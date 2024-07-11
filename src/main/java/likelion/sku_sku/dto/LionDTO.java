package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;

public class LionDTO {
// Request
    @Data
    public static class LionCreateRequest {
        @Schema(description = "이름", example = "한민규")
        private String name;

        @Schema(description = "이메일", example = "alswb0830@sungkyul.ac.kr")
        private String email;

        @Schema(description = "권한", example = "ADMIN_LION")
        private RoleType roleType;
    }

    @Data
    public static class LionUpdateRequest {
        @Schema(description = "lion 아이디", example = "1")
        private Long id;

        @Schema(description = "이름", example = "한민규")
        private String name;

        @Schema(description = "이메일", example = "alswb0830@sungkyul.ac.kr")
        private String email;

        @Schema(description = "권한", example = "ADMIN_LION")
        private RoleType roleType;
    }
    @Data
    public static class NameRequest {
        @Schema(description = "이름", example = "한민규")
        private String name;
    }
    @Data
    public static class EmailRequest {
        @Schema(description = "이메일", example = "alswb0830@sungkyul.ac.kr")
        private String email;
    }

// Response
    @Data
    @AllArgsConstructor
    public static class ResponseLionUpdate {
        @Schema(description = "이름", example = "한민규")
        private String name;

        @Schema(description = "이메일", example = "alswb0830@sungkyul.ac.kr")
        private String email;

        @Schema(description = "권한", example = "ADMIN_LION")
        private RoleType roleType;
    }

}
