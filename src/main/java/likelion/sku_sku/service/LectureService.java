package likelion.sku_sku.service;

import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static likelion.sku_sku.dto.JoinLectureFilesDTO.CreateJoinLectureFilesRequest;
import static likelion.sku_sku.dto.LectureDTO.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LionService lionService;
    private final JoinLectureFilesService joinLectureFilesService;

    // @PostMapping("/admin/lecture/add")
    @Transactional // 강의 안내물 생성 로직
    public Lecture createLecture(String bearer, createLectureRequest request) throws IOException {
        String writer = lionService.tokenToLionName(bearer.substring(7));
        Lecture lecture = new Lecture(request.getTrackType(), request.getTitle(), writer);
        lectureRepository.save(lecture);

        joinLectureFilesService.createJoinLectureFiles(lecture, request.getFiles());

        return lecture;
    }

    // @PutMapping("/admin/lecture/update")
    @Transactional // 강의 안내물 업데이트 로직
    public Lecture updateLecture(String bearer, updateLectureRequest request) throws IOException {
        String newWriter = lionService.tokenToLionName(bearer.substring(7));
        Lecture lecture = lectureRepository.findById(request.getId())
                .orElseThrow(InvalidIdException::new);

        TrackType newTrack = (request.getTrackType() != null ? request.getTrackType() : lecture.getTrack());
        String newTitle = (request.getTitle() != null && !request.getTitle().isEmpty() ? request.getTitle() : lecture.getTitle());
        lecture.update(newTrack, newTitle, newWriter);

        if (request.getFiles() != null && !request.getFiles().isEmpty()) {
            joinLectureFilesService.deleteByLecture(lecture);
            joinLectureFilesService.createJoinLectureFiles(lecture, request.getFiles());
        }

        return lecture;
    }

    @Transactional // 강의 안내물 조회 로직
    public ResponseLecture finaLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    lecture.incrementViewCount(); // 강의 안내물이 조회될 때 조회수 증가
                    lectureRepository.save(lecture);
                    return new ResponseLecture(
                            lecture.getId(),
                            lecture.getTrack(),
                            lecture.getTitle(),
                            lecture.getWriter(),
                            lecture.getViewCount(),
                            lecture.getCreateDate(),
                            lecture.getJoinLectureFiles().stream()
                                    .map(CreateJoinLectureFilesRequest::new)
                                    .collect(Collectors.toCollection(ArrayList::new)));
                })
                .orElseThrow(InvalidIdException::new);
    }

    // @DeleteMapping("/admin/lecture/delete")
    @Transactional // 강의 안내물 삭제 로직
    public void deleteLecture(Long id) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        lectureRepository.delete(lecture);
    }

    public List<ResponseLectureWithoutFiles> findAllLectureByTrackOrderByIdDesc(TrackType trackType) {
        List<Lecture> lectures = lectureRepository.findByTrackOrderByIdDesc(trackType);
        return lectures.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private ResponseLectureWithoutFiles convertToDTO(Lecture lecture) {
        return new ResponseLectureWithoutFiles(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getWriter(),
                lecture.getViewCount(),
                lecture.getCreateDate()
        );
    }
}
