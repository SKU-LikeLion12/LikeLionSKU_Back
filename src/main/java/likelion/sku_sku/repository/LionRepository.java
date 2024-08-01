package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Lion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LionRepository extends JpaRepository<Lion, Long> {
    Optional<Lion> findByEmail(String email);
}
