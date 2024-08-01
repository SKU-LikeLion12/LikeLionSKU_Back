package likelion.sku_sku.service;

import likelion.sku_sku.domain.Article;
import likelion.sku_sku.domain.LectureFile;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.LectureFileRepository;
import likelion.sku_sku.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFileService {
    private final LectureFileRepository lectureFileRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public LectureFile saveFile(Long articleId, MultipartFile file) throws IOException {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(InvalidIdException::new);
        LectureFile lectureFile = new LectureFile(article, file.getOriginalFilename(), file.getContentType(), file.getSize());
        return lectureFileRepository.save(lectureFile);
    }

    @Transactional
    public List<LectureFile> saveFiles(Long articleId, List<MultipartFile> files) throws IOException {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(InvalidIdException::new);
        List<LectureFile> lectureFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            LectureFile lectureFile = new LectureFile(article, file.getOriginalFilename(), file.getContentType(), file.getSize());
            lectureFiles.add(lectureFile);
        }
        return lectureFileRepository.saveAll(lectureFiles);
    }

    @Transactional
    public void deleteLectureFileById(Long id) {
        LectureFile lectureFile = lectureFileRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        lectureFileRepository.delete(lectureFile);
    }
}
