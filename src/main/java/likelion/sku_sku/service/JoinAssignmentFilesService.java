package likelion.sku_sku.service;

import likelion.sku_sku.domain.JoinAssignmentFiles;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.repository.JoinAssignmentFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinAssignmentFilesService {
    private final JoinAssignmentFilesRepository joinAssignmentFilesRepository;

// Transactional

    @Transactional
    public List<JoinAssignmentFiles> uploadJoinAssignmentFiles(SubmitAssignment submitAssignment, List<MultipartFile> files) throws IOException {
        List<JoinAssignmentFiles> joinAssignmentFilesList = new ArrayList<>();
        for (MultipartFile file : files) {
            JoinAssignmentFiles joinAssignmentFiles = new JoinAssignmentFiles(submitAssignment, file);
            joinAssignmentFilesList.add(joinAssignmentFiles);
        }
        joinAssignmentFilesRepository.saveAll(joinAssignmentFilesList);
        return joinAssignmentFilesList;
    }

    @Transactional
    public void deleteBySubmitAssignment(SubmitAssignment submitAssignment) {
        joinAssignmentFilesRepository.deleteBySubmitAssignment(submitAssignment);
    }

// Abstraction

    List<JoinAssignmentFiles> findBySubmitAssignment(SubmitAssignment submitAssignment) {
        return joinAssignmentFilesRepository.findBySubmitAssignment(submitAssignment);
    }
}
