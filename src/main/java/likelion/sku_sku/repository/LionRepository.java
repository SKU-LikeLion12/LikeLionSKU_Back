package likelion.sku_sku.repository;

import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.domain.enums.RoleType;
import likelion.sku_sku.domain.enums.TrackType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LionRepository extends JpaRepository<Lion, Long> {

    // 이메일로 사자 반환
    Optional<Lion> findByEmail(String email);
    List<Lion> findAllLionsByEmail(String email);


    // 트랙으로 사자 반환
    List<Lion> findWritersByTrack(TrackType track);

    // 트랙과 역할로 사자 반환
    List<Lion> findWritersByTrackAndRole(TrackType track, RoleType role);

}
