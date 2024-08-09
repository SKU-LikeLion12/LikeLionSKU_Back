package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity // 강의자료
public class LectureFile {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String writer;
    private int views = 0; // 객체의 id 값이 조회될 때 마다 조회수 1 증가

    @OneToMany(mappedBy = "lectureFile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<JoinLectureFiles> joinLectureFiles = new ArrayList<>();

    private LocalDate createDate; // YYYY-MM-DD
    private LocalDate updatedDate;

    public LectureFile(String title, String writer) {
        this.title = title;
        this.writer = writer;
        this.createDate = LocalDate.now();
        this.updatedDate = this.createDate;
    }
    public void update(String title, String writer) {
        this.title = title;
        this.writer = writer;
        this.updatedDate = LocalDate.now();
    }
}
