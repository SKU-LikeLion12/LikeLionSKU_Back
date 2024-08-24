package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Lion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LionRepository extends JpaRepository<Lion, Long> {

    // 이메일로 사자 반환
    Optional<Lion> findByEmail(String email);
}
