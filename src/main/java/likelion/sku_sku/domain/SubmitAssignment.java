package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

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

    private LocalDateTime createDate; // YYYY-MM-DD HH:MM:SS.nnnnnn
    @Enumerated(EnumType.STRING)
    private AssignmentStatus statusAssignment = AssignmentStatus.TODAY;

    public SubmitAssignment(TrackType track, Assignment assignment, String writer) {
        this.track = track;
        this.assignment = assignment;
        this.writer = writer;
        this.submitStatus = SubmitStatus.SUBMITTED;
        this.createDate = LocalDateTime.now();
        this.statusAssignment = AssignmentStatus.ING;
    }

    public void updatePassNonePass(PassNonePass passNonePass) {
        this.passNonePass = passNonePass;
    }
    public void updateStatusAssignment(AssignmentStatus statusAssignment) {
        this.statusAssignment = statusAssignment;
    }
}
