package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity // 과제 안내물
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assignment {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrackType track; // 트랙 BACKEND or FRONTEND or PM_DESIGN

    @Enumerated(EnumType.STRING)
    private AssignmentStatus assignmentStatus = AssignmentStatus.TODAY; // 과제 안내물 상태 TODAY or ING or DONE

    private String title; // 과제 안내물 제목

    private String subTitle; // 과제 안내물 부제목

    @Column(columnDefinition = "TEXT")
    private String description; // 과제 안내물 설명

    private LocalDate createDate; // YYYY-MM-DD // 과제 안내물 생성일

    private LocalDate dueDate; // YYYY-MM-DD // 과제 안내물 마감일

    // 생성자
    public Assignment(TrackType track, String title, String subTitle, String description, LocalDate dueDate) {
        this.track = track;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.dueDate = dueDate;
        this.createDate = LocalDate.now(); // 생성 당시 시간
    }

    // 업데이트
    public void update(String title, String subTitle, String description, LocalDate dueDate) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.dueDate = dueDate;
    }

    // 과제 안내물 상태 변경
    public void updateAssignmentStatus(AssignmentStatus newStatus) {
        this.assignmentStatus = newStatus;
    }

}
