package likelion.sku_sku.service;

import likelion.sku_sku.domain.LectureFile;
import likelion.sku_sku.domain.TrackType;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.LectureFileRepository;
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
    @Transactional
    public LectureFile saveFile(MultipartFile file) throws IOException {
        LectureFile lectureFile = new LectureFile(file.getOriginalFilename(), file.getContentType(), file.getSize());
        lectureFile.setFile(file);
        return lectureFileRepository.save(lectureFile);
    }
    @Transactional
    public List<LectureFile> saveFiles(List<MultipartFile> files) throws IOException {
        List<LectureFile> lectureFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            LectureFile lectureFile = new LectureFile(file.getOriginalFilename(), file.getContentType(), file.getSize());
            lectureFile.setFile(file);
            lectureFiles.add(lectureFile);
        }
        return lectureFileRepository.saveAll(lectureFiles);
    }

    @Transactional
    public void deleteLetureFileById(Long id) {
        LectureFile lectureFile = lectureFileRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        lectureFileRepository.delete(lectureFile);
    }
}
