package likelion.sku_sku.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class LectureFile {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;
    private String fileName;
    private String fileType;
    private long size;
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;

    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;
    public LectureFile(Article article, String fileName, String fileType, long size) {
        this.article = article;
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.createDate = LocalDateTime.now();
        this.updatedDate = this.createDate;
    }
    public void update(Article article, String fileName, String fileType, long size) {
        this.article = article;
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
