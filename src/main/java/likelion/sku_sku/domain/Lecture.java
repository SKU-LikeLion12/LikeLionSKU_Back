package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity // 강의
public class Lecture {
    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TrackType track;
    private String title;
    private String writer;
    private int viewCount = 0; // 객체의 id 값이 조회될 때 마다 조회수 1 증가

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<JoinLectureFiles> joinLectureFiles = new ArrayList<>();

    private LocalDate createDate; // YYYY-MM-DD

    public Lecture(TrackType track, String title, String writer) {
        this.track = track;
        this.title = title;
        this.writer = writer;
        this.createDate = LocalDate.now();
    }
    public void update(TrackType track, String title, String writer) {
        this.track = track;
        this.title = title;
        this.writer = writer;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
