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
@Entity // 강의자료
public class JoinLectureFiles {
    @Id @GeneratedValue
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture lecture; // 강의자료

    private String fileName; // 강의자료 이름

    private String fileType; // 강의자료 타입

    private long size = 0; // 강의자료 사이즈

    @Lob @Column(name = "file", columnDefinition = "LONGBLOB")
    private byte[] file; // 강의자료

    private LocalDateTime createDate; // YYYY-MM-DD HH:MM:SS.nnnnnn // 강의자료 생성일

    // 생성자
    public JoinLectureFiles(Lecture lecture, MultipartFile file) throws IOException {
        this.lecture = lecture;
        this.fileName = file.getOriginalFilename();
        this.fileType = file.getContentType();
        this.size = file.getSize();
        this.file = file.getBytes();
        this.createDate = LocalDateTime.now(); // 생성 당시 시간
    }

    // 강의자료 인코딩
    public String arrayToFile() {
        return FileUploadUtility.encodeFile(this.file);
    }
}
