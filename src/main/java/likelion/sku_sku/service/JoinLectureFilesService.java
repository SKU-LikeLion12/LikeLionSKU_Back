package likelion.sku_sku.service;

import likelion.sku_sku.domain.JoinLectureFiles;
import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.repository.JoinLectureFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinLectureFilesService {
    private final JoinLectureFilesRepository joinLectureFilesRepository;

    @Transactional
    public List<JoinLectureFiles> createJoinLectureFiles(Lecture lecture, List<MultipartFile> files) throws IOException {
        List<JoinLectureFiles> joinLectureFilesList = new ArrayList<>();
        for (MultipartFile file : files) {
            JoinLectureFiles joinLectureFiles = new JoinLectureFiles(lecture, file);
            joinLectureFilesList.add(joinLectureFiles);
        }
        joinLectureFilesRepository.saveAll(joinLectureFilesList);
        return joinLectureFilesList;
    }

    @Transactional
    public void deleteByLecture(Lecture lecture) {
        joinLectureFilesRepository.deleteByLecture(lecture);
    }
}
