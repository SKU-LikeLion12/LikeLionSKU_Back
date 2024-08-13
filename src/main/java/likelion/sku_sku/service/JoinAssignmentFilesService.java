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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinAssignmentFilesService {
    private final JoinAssignmentFilesRepository joinAssignmentFilesRepository;
    private final LionService lionService;

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
        joinAssignmentFilesRepository.deleteBySubmitAssignment(submitAssignment);  // 해당 Lecture에 연관된 모든 JoinLectureFiles 삭제
    }

    List<JoinAssignmentFiles> findBySubmitAssignment(SubmitAssignment submitAssignment) {
        return joinAssignmentFilesRepository.findBySubmitAssignment(submitAssignment);
    }


    public List<JoinAssignmentFiles> findAllJoinAssignmentFiles() {
        return joinAssignmentFilesRepository.findAll();
    }

    public JoinAssignmentFiles findJoinAssignmentFilesById(Long id) {
        return joinAssignmentFilesRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }

    @Transactional
    public void deleteJoinAssignmentFiles(Long id) {
        JoinAssignmentFiles joinAssignmentFiles = joinAssignmentFilesRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        joinAssignmentFilesRepository.delete(joinAssignmentFiles);
    }
}
