package likelion.sku_sku.domain;

import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity // 건의사항
public class Suggestion {
    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TrackType track;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;

    public Suggestion(TrackType track, String title, String content) {
        this.track = track;
        this.title = title;
        this.content = content;
        this.createDate = LocalDateTime.now();
        this.updatedDate = this.createDate;
    }

    public void update(TrackType track, String title, String content) {
        this.track = track;
        this.title = title;
        this.content = content;
        this.updatedDate = LocalDateTime.now();
    }
}
