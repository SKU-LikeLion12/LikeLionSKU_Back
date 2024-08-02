package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
