package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity // 과제 안내
@NoArgsConstructor
public class Assignment {
    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TrackType track;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private SubmitStatus submitStatus = SubmitStatus.UNSUBMITTED;
    @Enumerated(EnumType.STRING)
    private PassNonePass passNonePass = PassNonePass.FAIL;
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Feedback> feedback = new ArrayList<>();
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<JoinAssigmentFile> joinAssigmentFile = new ArrayList<>();

    private LocalDate createDate; // YYYY-MM-DD
    public Assignment(TrackType track, String title, String description, SubmitStatus submitStatus, PassNonePass passNonePass) {
        this.track = track;
        this.title = title;
        this.description = description;
        this.submitStatus = submitStatus;
        this.passNonePass = passNonePass;
        this.createDate = LocalDate.now();
    }
    public void update(String title) {
        this.title = title;
    }
}
