package likelion.sku_sku.domain;

import jakarta.persistence.*;
import likelion.sku_sku.domain.enums.RoleType;
import likelion.sku_sku.domain.enums.TrackType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity // 사자
public class Lion {
    @Id @GeneratedValue
    private Long id;

    private String name; // 사자 이름

    private String email; // 사자 이메일

    @Enumerated(EnumType.STRING)
    private TrackType track; // 트랙 BACKEND or FRONTEND or PM_DESIGN

    @Enumerated(EnumType.STRING)
    private RoleType role; // 권한 ADMIN_LION or BABY_LION or LEGACY_LION

    // 생성자
    public Lion(String name, String email, TrackType track, RoleType role) {
        this.name = name;
        this.email = email;
        this.track = track;
        this.role = role;
    }

    // 업데이트
    public void update(String name, String email, TrackType track, RoleType role) {
        this.name = name;
        this.email = email;
        this.track = track;
        this.role = role;
    }
}
