package likelion.sku_sku.service;

import likelion.sku_sku.domain.JoinLectureFiles;
import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.domain.enums.TrackType;
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
import static likelion.sku_sku.dto.LectureDTO.createLectureRequest;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final JoinLectureFilesRepository joinLectureFilesRepository;
    private final LionService lionService;

    private List<JoinLectureFiles> createJoinLectureFiles(Lecture lecture, List<MultipartFile> files) throws IOException {
        List<JoinLectureFiles> joinLectureFilesList = new ArrayList<>();
        for (MultipartFile file : files) {
            JoinLectureFiles joinLectureFiles = new JoinLectureFiles(lecture, file);
            joinLectureFilesList.add(joinLectureFiles);
        }
        return joinLectureFilesList;
    }

    @Transactional
    public List<Lecture> createLecture(String bearer, createLectureRequest request) throws IOException {
        String writer = lionService.tokenToLionName(bearer.substring(7));
        Lecture lecture = new Lecture(request.getTrackType(), request.getTitle(), writer);
        lectureRepository.save(lecture);

        List<JoinLectureFiles> joinLectureFiles = createJoinLectureFiles(lecture, request.getFiles());
        joinLectureFilesRepository.saveAll(joinLectureFiles);

        return List.of(lecture);
    }

    @Transactional
    public Lecture updateLecture(String bearer, updateLectureRequest request) throws IOException {
        String newWriter = lionService.tokenToLionName(bearer.substring(7));
        Lecture lecture = lectureRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 강의를 찾을 수 없습니다."));

        TrackType newTrack = (request.getTrackType() != null ? request.getTrackType() : lecture.getTrack());
        String newTitle = (request.getTitle() != null && !request.getTitle().isEmpty() ? request.getTitle() : lecture.getTitle());
        lecture.update(newTrack, newTitle, newWriter);

        if (request.getFiles() != null && !request.getFiles().isEmpty()) {
            joinLectureFilesRepository.deleteByLecture(lecture);
            List<JoinLectureFiles> updatedLectureFiles = createJoinLectureFiles(lecture, request.getFiles());
            joinLectureFilesRepository.saveAll(updatedLectureFiles);
        }

        return lecture;
    }

    public ResponseLecture finaLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    lecture.incrementViewCount();
                    lectureRepository.save(lecture);
                    return new ResponseLecture(
                            lecture.getId(),
                            lecture.getTrack(),
                            lecture.getTitle(),
                            lecture.getWriter(),
                            lecture.getViewCount(),
                            lecture.getCreateDate(),
                            lecture.getUpdatedDate(),
                            lecture.getJoinLectureFiles().stream()
                                    .map(CreateJoinLectureFilesRequest::new)
                                    .collect(Collectors.toCollection(ArrayList::new)));
                })
                .orElseThrow(InvalidIdException::new);
    }

    public List<Lecture> findAllLecture() {
        return lectureRepository.findAllWithFiles();
    }

    @Transactional
    public void deleteLectureById(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        lectureRepository.delete(lecture);
    }
}
