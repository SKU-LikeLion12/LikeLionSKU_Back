package likelion.sku_sku.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity // 과제 안내
@NoArgsConstructor
public class Assignment {
    @Id @GeneratedValue
    private Long id;
    private String title;

    public Assignment(String title) {
        this.title = title;
    }
    public void update(String title) {
        this.title = title;
    }
}
