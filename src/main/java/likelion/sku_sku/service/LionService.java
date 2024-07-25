package likelion.sku_sku.service;

import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.domain.RoleType;
import likelion.sku_sku.exception.InvalidEmailException;
import likelion.sku_sku.exception.InvalidLionIdException;
import likelion.sku_sku.exception.InvalidRoleException;
import likelion.sku_sku.repository.LionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static likelion.sku_sku.dto.LionDTO.ResponseLionUpdate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LionService {
    private final LionRepository lionRepository;

    @Transactional
    public Lion addLion(String name, String email, RoleType role) {
        if (role != RoleType.ADMIN_LION && role != RoleType.BABY_LION) {
            throw new InvalidRoleException();
        }
        if (lionRepository.findByEmail(email).isPresent()) {
            throw new InvalidEmailException();
        }
        Lion lion = new Lion(name, email, role);
        return lionRepository.save(lion);
    }

    @Transactional
    public Lion updateLion(Long id, String name, String email, RoleType role) {
        Lion lion = lionRepository.findById(id)
                .orElseThrow(InvalidLionIdException::new);
        String newName = (name != null && !name.isEmpty() ? name : lion.getName());
        String newEmail = (email != null && !email.isEmpty() ? email : lion.getEmail());
        if (!newEmail.equals(lion.getEmail()) && lionRepository.findByEmail(email).isPresent()) {
            throw new InvalidEmailException();
        }
        RoleType newRole = (role != null ? role : lion.getRole());
        if (newRole != RoleType.ADMIN_LION && newRole != RoleType.BABY_LION) {
            throw new InvalidRoleException();
        }
        lion.update(newName, newEmail, newRole);
        return lionRepository.save(lion);
    }

    public List<Lion> getAllLions() {
        return lionRepository.findAll();
    }

    public ResponseLionUpdate findLionById(Long id) {
        return lionRepository.findById(id)
                .map(lion -> new ResponseLionUpdate(
                        lion.getName(),
                        lion.getEmail(),
                        lion.getRole()))
                .orElse(null);
    }

    @Transactional
    public void deleteLionById(Long id) {
        Lion lion = lionRepository.findById(id)
                .orElseThrow(InvalidLionIdException::new);
        lionRepository.delete(lion);
    }
}
