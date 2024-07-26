package likelion.sku_sku.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import likelion.sku_sku.domain.Article;
import likelion.sku_sku.domain.RoleType;
import likelion.sku_sku.domain.TrackType;
import lombok.Data;

import java.time.LocalDateTime;

public class ArticleDTO {

    @Data
    public static class ArticleCreateRequest {
        @Schema(description = "트랙", example = "BACKEND")
        private TrackType track;

        @Schema(description = "제목", example = "백엔드 3주차 java 강의")
        private String title;

        @Schema(description = "내용", example = "java의 기본 개념과 .....")
        private String content;
    }

    @Data
    public static class ResponseArticle {
        @Schema(description = "트랙", example = "BACKEND")
        private TrackType track;
        @Schema(description = "제목", example = "백엔드 3주차 java 강의")
        private String title;
        @Schema(description = "내용", example = "java의 기본 개념과 .....")
        private String content;
        @Schema(description = "생성 시간", example = "2024-07-27")
        private LocalDateTime createDate;
        @Schema(description = "수정 여부", example = "true")
        private boolean isChange;
        public ResponseArticle(Article article) {
            this.track = article.getTrack();
            this.title = article.getTitle();
            this.content = article.getContent();
            this.createDate = article.getCreateDate();

            if (article.getCreateDate().equals(article.getUpdatedDate())) {
                this.isChange = false;
            } else {
                this.isChange = true;
            }
        }
    }
}
