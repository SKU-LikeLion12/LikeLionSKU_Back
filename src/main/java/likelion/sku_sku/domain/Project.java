package likelion.sku_sku.domain;

import jakarta.persistence.*;
import likelion.sku_sku.service.ImageUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Getter
@NoArgsConstructor
public class Project {
    @Id @GeneratedValue
    private Long id;
    private String classTh;
    private String title;
    private String subTitle;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    public Project(String classTh, String title, String subTitle, byte[] image) {
        this.classTh = classTh;
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
    }
    public String arrayToImage() {
        return ImageUtility.encodeImage(this.image);
    }

    public void changeProject(String classTh, String title, String subTitle) {
        this.classTh = classTh;
        this.title = title;
        this.subTitle = subTitle;
    }
    public void setImage(MultipartFile image) throws IOException {
        this.image = image.getBytes();
    }
}
