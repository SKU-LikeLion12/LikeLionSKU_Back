package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter // 제출된 과제 피드백
public class Feedback {
    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitAssignment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubmitAssignment submitAssignment; // 제출된 과제

    @Column(columnDefinition = "TEXT")
    private String content; // 피드백 내용

    private LocalDateTime createDate; // YYYY-MM-DD HH:MM:SS.nnnnnn // 피드백 생성일

    // 생성자
    public Feedback(SubmitAssignment submitAssignment, String content) {
        this.submitAssignment = submitAssignment;
        this.content = content;
        this.createDate = LocalDateTime.now(); // 생성 당시 시간
    }

    // 업데이트
    public void update(String content) {
        this.content = content;
    }
}
