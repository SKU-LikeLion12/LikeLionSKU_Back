package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.LectureFile;
import likelion.sku_sku.service.LectureFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static likelion.sku_sku.dto.LectureFileDTO.uploadLectureFileRequest;

@RestController
@RequiredArgsConstructor
@PreAuthorize(value = "hasAnyRole('ADMIN_LION', 'BABY_LION')")
@Tag(name = "관리자 페이지: LectureFile 관련")
public class LectureFileController {

    private final LectureFileService lectureFileService;

    @PostMapping("/upload")
    public ResponseEntity<List<LectureFile>> uploadFiles(@ModelAttribute uploadLectureFileRequest lectureFileDto) throws IOException {
        List<MultipartFile> files = lectureFileDto.getFiles();
        List<LectureFile> savedFiles = lectureFileService.saveFiles(lectureFileDto.getArticleId(), files);
        return ResponseEntity.ok(savedFiles);
    }
}
