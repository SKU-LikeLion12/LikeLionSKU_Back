package likelion.sku_sku.domain;

import jakarta.persistence.*;
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
public class LectureFile {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "lecture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture;
    private String fileName;
    private String fileType;
    private long size;
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;

    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;
    public LectureFile(Lecture lecture, String fileName, String fileType, long size) {
        this.lecture = lecture;
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.createDate = LocalDateTime.now();
        this.updatedDate = this.createDate;
    }
    public void update(Lecture lecture, String fileName, String fileType, long size) {
        this.lecture = lecture;
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.updatedDate = LocalDateTime.now();

    }

    public void setFile(MultipartFile file) throws IOException {
        this.file = file.getBytes();
        this.size = file.getSize();
    }
}
