package likelion.sku_sku.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Lion {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public Lion(String name, String email, RoleType role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void update(String name, String email, RoleType role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void roleUpdate(RoleType role) {
        this.role = role;
    }
}
