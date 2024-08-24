package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.domain.enums.TrackType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    // id로 강의 안내물 반환 (강의자료를 제외한)
    @EntityGraph(attributePaths = {"joinLectureFiles"})
    Optional<Lecture> findById(Long id);

    // 전체 강의 안내물 리스트 반환 (강의자료를 제외한)
    @EntityGraph(attributePaths = {"joinLectureFiles"})
    List<Lecture> findAll();

    // 트랙별 강의 안내물 리스트 내림차순 반환 (강의자료를 제외한)
    @EntityGraph(attributePaths = {"joinLectureFiles"})
    List<Lecture> findByTrackOrderByIdDesc(TrackType trackType);
}
