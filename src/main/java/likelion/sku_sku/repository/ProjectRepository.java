package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByTitle(String title);
    List<Project> findAllByOrderByIdDesc();
}
