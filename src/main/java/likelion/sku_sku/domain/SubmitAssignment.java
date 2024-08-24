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
@NoArgsConstructor
@Entity // 제출된 과제
public class SubmitAssignment {
    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrackType track; // 트랙 BACKEND or FRONTEND or PM_DESIGN

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Assignment assignment; // 강의 안내물

    private String writer; // 제출된 과제 작성자

    @Enumerated(EnumType.STRING)
    private SubmitStatus submitStatus = SubmitStatus.UNSUBMITTED; // 과제 제출 상태 SUBMITTED or UNSUBMITTED

    @Enumerated(EnumType.STRING)
    private PassNonePass passNonePass = PassNonePass.FAIL; // 제출된 과제 통과 상태 PASS or FAIL

    @Enumerated(EnumType.STRING)
    private AssignmentStatus statusAssignment = AssignmentStatus.TODAY; // 개별적 과제 안내물 상태를 위한 과제 제출 상태에 따른 별도 상태  TODAY or ING or DONE

    private LocalDateTime createDate; // YYYY-MM-DD HH:MM:SS.nnnnnn // 과제 제출 시간

    // 생성자
    public SubmitAssignment(TrackType track, Assignment assignment, String writer) {
        this.track = track;
        this.assignment = assignment;
        this.writer = writer;
        this.submitStatus = SubmitStatus.SUBMITTED; // 과제 제출시 과제 상태 제출로 변경
        this.createDate = LocalDateTime.now(); // 과제 제출 당시 시간
        this.statusAssignment = AssignmentStatus.ING; // 과제 제출시 별도 상태 ING로 변경
    }

    // 제출된 과제 통과 상태 변경
    public void updatePassNonePass(PassNonePass passNonePass) {
        this.passNonePass = passNonePass;
    }

    // 별도 상태 변경
    public void updateStatusAssignment(AssignmentStatus statusAssignment) {
        this.statusAssignment = statusAssignment;
    }
}
