package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity // 과제 제출
@NoArgsConstructor
public class SubmitAssignment {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrackType track;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Assignment assignment;

    private String writer;

    @Enumerated(EnumType.STRING)
    private SubmitStatus submitStatus = SubmitStatus.UNSUBMITTED;

    @Enumerated(EnumType.STRING)
    private PassNonePass passNonePass = PassNonePass.FAIL;

    @JsonManagedReference
    @OneToMany(mappedBy = "submitAssignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedback = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "submitAssignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinAssignmentFiles> joinAssignmentFiles = new ArrayList<>();

    private LocalDateTime createDate; // YYYY-MM-DD HH:MM:SS.nnnnnn
    public SubmitAssignment(TrackType track, Assignment assignment, String writer) {
        this.track = track;
        this.assignment = assignment;
        this.writer = writer;
        this.createDate = LocalDateTime.now();
    }
}
