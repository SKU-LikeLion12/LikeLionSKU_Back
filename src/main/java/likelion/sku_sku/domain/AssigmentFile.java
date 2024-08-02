package likelion.sku_sku.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class AssigmentFile {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "assignment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Assignment assignment;
    private String fileName;
    private String fileType;
    private long size;
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;

    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file;
    public AssigmentFile(Assignment assignment, String fileName, String fileType, long size) {
        this.assignment = assignment;
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.createDate = LocalDateTime.now();
        this.updatedDate = this.createDate;
    }
    public void update(Assignment assignment, String fileName, String fileType, long size) {
        this.assignment = assignment;
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
