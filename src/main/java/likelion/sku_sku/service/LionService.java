package likelion.sku_sku.service;

import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.domain.enums.RoleType;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.dto.LionDTO;
import likelion.sku_sku.exception.InvalidEmailException;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.exception.InvalidLionException;
import likelion.sku_sku.repository.LionRepository;
import likelion.sku_sku.security.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static likelion.sku_sku.dto.LionDTO.*;
import static likelion.sku_sku.dto.LionDTO.ResponseLionUpdate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LionService {
    private final LionRepository lionRepository;
    private final JwtUtility jwtUtility;

    public String tokenToLionName(String token) {
        Lion lion = lionRepository.findByEmail(jwtUtility.getEmailFromToken(token))
                .orElseThrow(InvalidLionException::new);
        return lion.getName();
    }

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

    @Transactional
    public void deleteLion(Long id) {
        Lion lion = lionRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        lionRepository.delete(lion);
    }

    public List<Lion> getAllLions() {
        return lionRepository.findAll();
    }

    public List<String> findWritersByTrack(TrackType track) {
        List<Lion> lions = lionRepository.findWritersByTrack(track);
        return lions.stream()
                .map(Lion::getName)
                .toList();
    }

    public List<String> findWritersByTrackAndBaby(TrackType track) {
        List<Lion> lions = lionRepository.findWritersByTrackAndRole(track, RoleType.BABY_LION);
        return lions.stream()
                .map(Lion::getName)
                .toList();
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
}
