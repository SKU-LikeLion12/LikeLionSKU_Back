package likelion.sku_sku.service;

import likelion.sku_sku.domain.JoinAssignmentFiles;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.JoinAssignmentFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinAssignmentFilesService {
    private final JoinAssignmentFilesRepository joinAssignmentFilesRepository;

    @Transactional
    public JoinAssignmentFiles uploadFile(SubmitAssignment submitAssignment, MultipartFile file) throws IOException {
        JoinAssignmentFiles joinAssignmentFiles = new JoinAssignmentFiles(submitAssignment, file);
        return joinAssignmentFilesRepository.save(joinAssignmentFiles);
    }

    public List<JoinAssignmentFiles> getAllFiles() {
        return joinAssignmentFilesRepository.findAll();
    }

    public JoinAssignmentFiles getFileById(Long id) {
        return joinAssignmentFilesRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }

    @Transactional
    public void deleteFile(Long id) {
        JoinAssignmentFiles joinAssignmentFiles = joinAssignmentFilesRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        joinAssignmentFilesRepository.delete(joinAssignmentFiles);
    }
}
