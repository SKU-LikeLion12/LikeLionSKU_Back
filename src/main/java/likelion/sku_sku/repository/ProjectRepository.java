package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 제목으로 프로젝트 반환
    Optional<Project> findByTitle(String title);

    // 전체 프로젝트 리스트 내림차순 반환
    List<Project> findAllByOrderByIdDesc();
}
