package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity // 과제 안내
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assignment {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrackType track;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus assignmentStatus = AssignmentStatus.TODAY;

    private String title;

    private String subTitle;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate createDate; // YYYY-MM-DD
    private LocalDate dueDate; // YYYY-MM-DD

    public Assignment(TrackType track, String title, String subTitle, String description, LocalDate dueDate) {
        this.track = track;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.dueDate = dueDate;
        this.createDate = LocalDate.now();
    }

    public void update(String title, String subTitle, String description, LocalDate dueDate) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.dueDate = dueDate;
    }

    public void updateAssignmentStatus(AssignmentStatus newStatus) {
        this.assignmentStatus = newStatus;
    }

}
