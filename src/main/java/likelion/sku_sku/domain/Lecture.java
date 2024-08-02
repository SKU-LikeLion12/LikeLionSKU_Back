package likelion.sku_sku.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Lecture {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String writer;
    private int view = 0; // 객체의 id 값이 조회될 때 마다 조회수 1 증가
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;

    public Lecture(String title, String writer) {
        this.title = title;
        this.writer = writer;
        this.createDate = LocalDateTime.now();
        this.updatedDate = createDate;
    }
}
