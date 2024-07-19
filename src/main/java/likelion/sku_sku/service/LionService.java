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
        if (lionRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("그 email 이미 있지롱");
        }
        Lion lion = new Lion(name, email, role);
        return lionRepository.save(lion);
    }
    @Transactional
    public Lion updateLion(Long id, String name, String email, RoleType role) {
        Lion lion = lionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그런 Lion 없는디요"));

        if (lionRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("그 email 이미 있지롱");
        }

        String newName = (name != null && !name.isEmpty() ? name : lion.getName());
        String newEmail = (email != null && !email.isEmpty() ? email : lion.getEmail());
        RoleType newRole = (role != null ? role : lion.getRole());
        lion.update(newName, newEmail, newRole);
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
    public void deleteLion(String email) {
        Lion lion = lionRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("그런 email 가진 Lion 없는디요"));
        lionRepository.delete(lion);
    }

//    @Transactional
//    public Lion updateLionRole(Long id, RoleType roleType) {
//        Lion lion = lionRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("그런 Lion 없는디요"));
//        lion.roleUpdate(roleType);
//        return lionRepository.save(lion);
//    }
}
