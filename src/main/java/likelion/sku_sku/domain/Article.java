package likelion.sku_sku.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Article {
    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TrackType track;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;

    public Article(TrackType track, String title, String content) {
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
