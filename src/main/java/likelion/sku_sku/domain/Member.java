package likelion.sku_sku.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String major;
    private String studentId;
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.BABY_LION;
}
