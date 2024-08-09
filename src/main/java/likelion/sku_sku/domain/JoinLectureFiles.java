package likelion.sku_sku.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor
@Entity
public class JoinLectureFiles {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_file_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private LectureFile lectureFile;

    @Lob
    @Column(name = "file", columnDefinition = "TEXT") // 인코딩된 파일 데이터를 TEXT로 저장
    private String encodedFile;

    private String fileName;
    private String fileType;
    private long size = 0;

    // 기존 MultipartFile을 받는 생성자 대신, 인코딩된 파일 데이터를 받는 생성자 추가
    public JoinLectureFiles(LectureFile lectureFile, String encodedContent, String fileName, String fileType, long size) {
        this.lectureFile = lectureFile;
        this.encodedFile = encodedContent;
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
    }

    // 기존에 있던 생성자도 여전히 필요하다면 유지하세요
//    public JoinLectureFiles(LectureFile lectureFile, MultipartFile file) throws IOException {
//        this.lectureFile = lectureFile;
//        this.fileName = file.getOriginalFilename();
//        this.fileType = file.getContentType();
//        this.size = file.getSize();
//        this.encodedFile = new String(Base64.encodeBase64(file.getBytes()));
//    }

    public byte[] getDecodedFile() {
        return Base64.decodeBase64(this.encodedFile); // 인코딩된 파일을 디코딩하여 반환
    }
}
