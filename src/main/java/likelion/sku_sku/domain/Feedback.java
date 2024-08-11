package likelion.sku_sku.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor
@Getter // 과제 피드백
public class Feedback {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submit_assignment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubmitAssignment submitAssignment;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Feedback(SubmitAssignment submitAssignment, String content) {
        this.submitAssignment = submitAssignment;
        this.content = content;
    }
}
