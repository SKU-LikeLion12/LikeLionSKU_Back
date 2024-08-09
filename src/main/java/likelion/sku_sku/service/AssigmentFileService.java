package likelion.sku_sku.service;

import likelion.sku_sku.repository.AssigmentFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssigmentFileService {
    private final AssigmentFileRepository assigmentFileRepository;
}
