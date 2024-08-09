package likelion.sku_sku.service;

import likelion.sku_sku.domain.JoinLectureFiles;
import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.dto.JoinLectureFilesDTO;
import likelion.sku_sku.dto.LectureDTO;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.JoinLectureFilesRepository;
import likelion.sku_sku.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static likelion.sku_sku.dto.JoinLectureFilesDTO.*;
import static likelion.sku_sku.dto.LectureDTO.*;
import static likelion.sku_sku.dto.LectureDTO.uploadLectureRequest;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final JoinLectureFilesRepository joinLectureFilesRepository;
    private final LionService lionService;
    @Transactional
    public List<Lecture> uploadLectureFiles(String bearer, uploadLectureRequest request) throws IOException {
        String writer = lionService.tokenToLionName(bearer.substring(7));
        Lecture lecture = new Lecture(request.getTitle(), writer);
        lectureRepository.save(lecture);

        List<JoinLectureFiles> joinLectureFilesList = new ArrayList<>();
        for (MultipartFile file : request.getFiles()) {
            JoinLectureFiles joinLectureFiles = new JoinLectureFiles(lecture, file);
            joinLectureFilesList.add(joinLectureFiles);
        }
        joinLectureFilesRepository.saveAll(joinLectureFilesList);
        return List.of(lecture);
    }

    public ResponseLecture finaLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> new ResponseLecture(
                        lecture.getId(),
                        lecture.getTitle(),
                        lecture.getWriter(),
                        lecture.getViews(),
                        lecture.getCreateDate(),
                        lecture.getUpdatedDate(),
                        lecture.getJoinLectureFiles().stream()
                                .map(CreateJoinLectureFilesRequest::new)
                                .collect(Collectors.toCollection(ArrayList::new))))
                .orElseThrow(InvalidIdException::new);

    }

    public List<Lecture> findAllLecture() {
        return lectureRepository.findAll();
    }

    @Transactional
    public void deleteLectureById(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        lectureRepository.delete(lecture);
    }
}
