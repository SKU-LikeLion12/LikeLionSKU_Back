package likelion.sku_sku.service;

import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.domain.RoleType;
import likelion.sku_sku.repository.LionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LionService {
    private final LionRepository lionRepository;

    @Transactional
    public Lion addLion(String name, String email, RoleType role) {
        if (lionRepository.findByEmail(email).isPresent()) { // 이메일 중복 여부 확인
            throw new IllegalArgumentException("Email already exists");
        }
        Lion lion = new Lion(name, email, role);
        return lionRepository.save(lion);
    }

    public List<Lion> getLionsByName(String name) {
        return lionRepository.findByName(name);
    }

    public Optional<Lion> getLionByEmail(String email) {
        return lionRepository.findByEmail(email);
    }

    public List<Lion> getAllLions() {
        return lionRepository.findAll();
    }

    @Transactional
    public Lion updateLion(Long id, String name, String email, RoleType role) {
        Lion lion = lionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lion not found"));
        lion.update(name, email, role);
        return lionRepository.save(lion);
    }

    @Transactional
    public void deleteLion(String email) {
        Lion lion = lionRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Lion not found with email " + email));
        lionRepository.delete(lion);
    }

    @Transactional
    public Lion updateLionRole(Long id, RoleType roleType) {
        Lion lion = lionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lion not found"));
        lion.roleUpdate(roleType);
        return lionRepository.save(lion);
    }

}
