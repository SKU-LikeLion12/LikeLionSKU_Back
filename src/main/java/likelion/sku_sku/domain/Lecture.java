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
@Entity // 강의 안내물
public class Lecture {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrackType track; // 트랙 BACKEND or FRONTEND or PM_DESIGN

    private String title; // 강의 안내물 이름

    private String writer; // 강의 안내물 작성자

    private int viewCount = 0; // 강의 안내물 조회수

    @JsonManagedReference
    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinLectureFiles> joinLectureFiles = new ArrayList<>(); // 강의자료

    private LocalDate createDate; // YYYY-MM-DD 강의 안내물 생성일

    // 생성자
    public Lecture(TrackType track, String title, String writer) {
        this.track = track;
        this.title = title;
        this.writer = writer;
        this.createDate = LocalDate.now(); // 생성 당시 시간
    }

    // 업데이트
    public void update(TrackType track, String title, String writer) {
        this.track = track;
        this.title = title;
        this.writer = writer;
    }

    // 조회수 증가
    public void incrementViewCount() {
        this.viewCount+=1;
    }
}
