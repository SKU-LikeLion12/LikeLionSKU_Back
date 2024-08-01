package likelion.sku_sku.service;

import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.domain.RoleType;
import likelion.sku_sku.domain.TrackType;
import likelion.sku_sku.exception.*;
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
    public Lion addLion(String name, String email, TrackType track, RoleType role) {
        if (lionRepository.findByEmail(email).isPresent()) {
            throw new InvalidEmailException();
        }
        Lion lion = new Lion(name, email, track, role);
        return lionRepository.save(lion);
    }

    @Transactional
    public Lion updateLion(Long id, String name, String email, TrackType track, RoleType role) {
        Lion lion = lionRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        String newName = (name != null && !name.isEmpty() ? name : lion.getName());
        String newEmail = (email != null && !email.isEmpty() ? email : lion.getEmail());
        TrackType newTrack = (track != null ? track : lion.getTrack());
        RoleType newRole = (role != null ? role : lion.getRole());

        lion.update(newName, newEmail, newTrack, newRole);
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
                        lion.getTrack(),
                        lion.getRole()))
                .orElse(null);
    }

    @Transactional
    public void deleteLionById(Long id) {
        Lion lion = lionRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        lionRepository.delete(lion);
    }
}
