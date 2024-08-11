package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter // 과제 피드백
public class Feedback {
    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitAssignment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubmitAssignment submitAssignment;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate; // YYYY-MM-DD HH:MM:SS.nnnnnn

    public Feedback(SubmitAssignment submitAssignment, String content) {
        this.submitAssignment = submitAssignment;
        this.content = content;
        this.createDate = LocalDateTime.now();
    }

    public void update(String content) {
        this.content = content;
    }
}
