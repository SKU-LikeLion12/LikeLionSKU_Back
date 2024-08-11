package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity // 과제 안내
@NoArgsConstructor
public class Assigment {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TrackType track;
    @Enumerated(EnumType.STRING)
    private AssignmentStatus assignmentStatus = AssignmentStatus.TODAY;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SubmitAssignment> submitAssignment = new ArrayList<>();
    private LocalDate createDate; // YYYY-MM-DD

    public Assigment(TrackType track, String title, String description) {
        this.track = track;
        this.title = title;
        this.description = description;
        this.createDate = LocalDate.now();
    }
}