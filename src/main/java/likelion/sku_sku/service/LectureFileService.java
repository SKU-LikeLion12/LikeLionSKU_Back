package likelion.sku_sku.service;

import likelion.sku_sku.domain.JoinLectureFiles;
import likelion.sku_sku.domain.LectureFile;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.JoinLectureFilesRepository;
import likelion.sku_sku.repository.LectureFileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
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
    private final JoinLectureFilesRepository joinLectureFilesRepository;
    private final LionService lionService;

    @Transactional
    public List<LectureFile> saveFiles(String bearer, String title, List<MultipartFile> files) throws IOException {
        String writer = lionService.tokenToLionName(bearer.substring(7));
        LectureFile lectureFile = new LectureFile(title, writer);
        lectureFileRepository.save(lectureFile);

        List<JoinLectureFiles> joinLectureFilesList = new ArrayList<>();
        for (MultipartFile file : files) {
            // 파일 내용을 Base64로 인코딩
            String encodedContent = Base64.encodeBase64String(file.getBytes());
            JoinLectureFiles joinLectureFile = new JoinLectureFiles(lectureFile, encodedContent, file.getOriginalFilename(), file.getContentType(), file.getSize());
            joinLectureFilesList.add(joinLectureFile);
        }
        joinLectureFilesRepository.saveAll(joinLectureFilesList);
        return List.of(lectureFile);
    }

    @Transactional
    public List<LectureFile> updateFiles(String bearer, Long lectureFileId, String title, List<MultipartFile> files) throws IOException {
        String newWriter = lionService.tokenToLionName(bearer.substring(7));
        LectureFile lectureFile = lectureFileRepository.findById(lectureFileId)
                .orElseThrow(InvalidIdException::new);
        String newTitle = (title != null && !title.isEmpty() ? title : lectureFile.getTitle());
        lectureFile.update(newTitle, newWriter);

        // 기존 파일들 삭제
        joinLectureFilesRepository.deleteAll(lectureFile.getJoinLectureFiles());
        lectureFile.getJoinLectureFiles().clear();

        // 새로운 파일들 추가
        List<JoinLectureFiles> joinLectureFilesList = new ArrayList<>();
        for (MultipartFile file : files) {
            // 파일 내용을 Base64로 인코딩
            String encodedContent = Base64.encodeBase64String(file.getBytes());
            JoinLectureFiles joinLectureFile = new JoinLectureFiles(lectureFile, encodedContent, file.getOriginalFilename(), file.getContentType(), file.getSize());
            joinLectureFilesList.add(joinLectureFile);
        }
        joinLectureFilesRepository.saveAll(joinLectureFilesList);
        return List.of(lectureFile);
    }

    public List<LectureFile> findAllLectureFiles() {
        return lectureFileRepository.findAll();
    }

    public LectureFile findLectureFileById(Long id) {
        return lectureFileRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }

    @Transactional
    public void deleteLectureFileById(Long id) {
        LectureFile lectureFile = lectureFileRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        lectureFileRepository.delete(lectureFile);
    }
}
