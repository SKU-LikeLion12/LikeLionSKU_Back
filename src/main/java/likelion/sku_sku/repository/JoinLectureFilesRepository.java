package likelion.sku_sku.repository;

import likelion.sku_sku.domain.JoinLectureFiles;
import likelion.sku_sku.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinLectureFilesRepository extends JpaRepository<JoinLectureFiles, Long> {
    void deleteByLecture(Lecture lecture);
}
