package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import likelion.sku_sku.service.FileUploadUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@NoArgsConstructor
@Getter // 제출된 과제 파일
public class JoinAssignmentFiles {
    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitAssignment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubmitAssignment submitAssignment; // 제출된 과제

    private String fileName; // 제출된 과제 파일 이름

    private String fileType; // 제출된 과제 파일 타입

    private long size = 0; // 제출된 과제 파일 사이즈

    @Lob @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file; // 제출된 과제 파일

    // 생성자
    public JoinAssignmentFiles(SubmitAssignment submitAssignment, MultipartFile file) throws IOException {
        this.submitAssignment = submitAssignment;
        this.fileName = file.getOriginalFilename();
        this.fileType = file.getContentType();
        this.size = file.getSize();
        this.file = file.getBytes();
    }

    // 제출된 과제 파일 인코딩
    public String arrayToFile() {
        return FileUploadUtility.encodeFile(this.file);
    }
}
