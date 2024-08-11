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
@Getter // 과제 파일 제출
public class JoinAssignmentFiles {
    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitAssignment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubmitAssignment submitAssignment;

    private String fileName;

    private String fileType;

    private long size = 0;

    @Lob @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;

    public JoinAssignmentFiles(SubmitAssignment submitAssignment, MultipartFile file) throws IOException {
        this.submitAssignment = submitAssignment;
        this.fileName = file.getOriginalFilename();
        this.fileType = file.getContentType();
        this.size = file.getSize();
        this.file = file.getBytes();
    }

    public String arrayToFile() {
        return FileUploadUtility.encodeFile(this.file);
    }
}
