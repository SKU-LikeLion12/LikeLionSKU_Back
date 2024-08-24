package likelion.sku_sku.repository;

import likelion.sku_sku.domain.JoinLectureFiles;
import likelion.sku_sku.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinLectureFilesRepository extends JpaRepository<JoinLectureFiles, Long> {

    // 강의 안내물로 강의자료 삭제
    void deleteByLecture(Lecture lecture);
}
