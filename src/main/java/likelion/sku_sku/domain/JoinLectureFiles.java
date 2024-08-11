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
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class JoinLectureFiles {
    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;

    private String fileName;

    private String fileType;

    private long size = 0;

    @Lob @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;

    private LocalDateTime createDate; // YYYY-MM-DD HH:MM:SS.nnnnnn


    public JoinLectureFiles(Lecture lecture, MultipartFile file) throws IOException {
        this.lecture = lecture;
        this.fileName = file.getOriginalFilename();
        this.fileType = file.getContentType();
        this.size = file.getSize();
        this.file = file.getBytes();
        this.createDate = LocalDateTime.now();
    }

    public String arrayToFile() {
        return FileUploadUtility.encodeFile(this.file);
    }
}
