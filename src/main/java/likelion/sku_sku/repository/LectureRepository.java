package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.domain.enums.TrackType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @EntityGraph(attributePaths = {"joinLectureFiles"})
    Optional<Lecture> findById(Long id);

    @EntityGraph(attributePaths = {"joinLectureFiles"})
    List<Lecture> findAll();

    @EntityGraph(attributePaths = {"joinLectureFiles"})
    List<Lecture> findByTrackOrderByIdDesc(TrackType trackType);
}
