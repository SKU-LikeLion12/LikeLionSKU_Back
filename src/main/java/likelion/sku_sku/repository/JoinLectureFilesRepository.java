package likelion.sku_sku.repository;

import likelion.sku_sku.domain.JoinLectureFiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinLectureFilesRepository extends JpaRepository<JoinLectureFiles, Long> {
    List<JoinLectureFiles> findByLectureId(Long lectureId);

}
