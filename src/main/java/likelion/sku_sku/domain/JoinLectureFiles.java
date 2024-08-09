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

@Getter
@NoArgsConstructor
@Entity
public class JoinLectureFiles {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Lecture lecture;
    private String fileName;
    private String fileType;
    private long size = 0;
    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;

    public JoinLectureFiles(Lecture lecture, MultipartFile file) throws IOException {
        this.lecture = lecture;
        this.fileName = file.getOriginalFilename();
        this.fileType = file.getContentType();
        this.size = file.getSize();
        this.file = file.getBytes();
    }

    public String arrayToFile() {
        return FileUploadUtility.encodeFile(this.file);
    }
}
