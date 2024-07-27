package likelion.sku_sku.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class LectureFileDTO {

    @Data
    public static class uploadLectureFileRequest {
        private List<MultipartFile> files;
    }
}
