package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String writer;
    @Enumerated(EnumType.STRING)
    private SubmitStatus submitStatus = SubmitStatus.UNSUBMITTED;
    @Enumerated(EnumType.STRING)
    private PassNonePass passNonePass = PassNonePass.FAIL;
    @OneToMany(mappedBy = "submitAssignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Feedback> feedback = new ArrayList<>();
    @OneToMany(mappedBy = "submitAssignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<JoinAssigmentFile> joinAssigmentFile = new ArrayList<>();

    private LocalDate createDate; // YYYY-MM-DD
    public SubmitAssignment(TrackType track,String writer, SubmitStatus submitStatus, PassNonePass passNonePass) {
        this.track = track;
        this.writer = writer;
        this.submitStatus = submitStatus;
        this.passNonePass = passNonePass;
        this.createDate = LocalDate.now();
    }
}
