package likelion.sku_sku.service;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.repository.SubmitAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static likelion.sku_sku.dto.SubmitAssignmentDTO.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmitAssignmentService {
    private final SubmitAssignmentRepository submitAssignmentRepository;
    private final LionService lionService;
    private final JoinAssignmentFilesService joinAssignmentFilesService;
    private final AssignmentService assignmentService;
    @Transactional // 과제 제출
    public SubmitAssignment createSubmitAssignment(String bearer, Long assignmentId, List<MultipartFile> files) throws IOException {
        String writer = lionService.tokenToLionName(bearer.substring(7));
        Assignment assignment = assignmentService.findAssignmentById(assignmentId);
        SubmitAssignment submitAssignment = new SubmitAssignment(assignment.getTrack(), assignment, writer);
        submitAssignmentRepository.save(submitAssignment);

        joinAssignmentFilesService.uploadJoinAssignmentFiles(submitAssignment, files);

        return submitAssignment;
    }

    @Transactional // 과제 수정
    public SubmitAssignment updateSubmitAssignment(Long id, List<MultipartFile> files) throws IOException {
        SubmitAssignment submitAssignment = submitAssignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        if (files != null && files.isEmpty()) {
            joinAssignmentFilesService.deleteBySubmitAssignment(submitAssignment);
            joinAssignmentFilesService.uploadJoinAssignmentFiles(submitAssignment, files);
        }
        return submitAssignment;
    }

    @Transactional // 과제 통과 여부
    public SubmitAssignment decidePassStatus(Long submitAssignmentId) {
        SubmitAssignment submitAssignment = submitAssignmentRepository.findById(submitAssignmentId)
                .orElseThrow(InvalidIdException::new);
        submitAssignment.decidePassStatus();
        return submitAssignmentRepository.save(submitAssignment);
    }


    public List<SubmitAssignment> findSubmittedAssignmentsByWriter(String writer) {
        return submitAssignmentRepository.findByWriter(writer);
    }

    public Map<String, List<SubmitAssignment>> findAllAssignmentsByWriter(String writer) {
        List<SubmitAssignment> todayAssignments = submitAssignmentRepository.findByWriterAndAssignment_AssignmentStatus(writer, AssignmentStatus.TODAY);
        List<SubmitAssignment> ingAssignments = submitAssignmentRepository.findByWriterAndAssignment_AssignmentStatus(writer, AssignmentStatus.ING);
        List<SubmitAssignment> doneAssignments = submitAssignmentRepository.findByWriterAndAssignment_AssignmentStatus(writer, AssignmentStatus.DONE);

        Map<String, List<SubmitAssignment>> result = new HashMap<>();
        result.put("today", todayAssignments);
        result.put("ing", ingAssignments);
        result.put("done", doneAssignments);

        return result;
    }

    public List<SubmitAssignment> findAssignmentsByWriterAndStatus(String writer, AssignmentStatus status) {
        return submitAssignmentRepository.findByWriterAndAssignment_AssignmentStatus(writer, status);
    }


    public List<SubmitAssignment> findAssignmentsByTrackType(TrackType trackType) {
        return submitAssignmentRepository.findByAssignment_Track(trackType);
    }

    public SubmitAssignment findSubmitAssignmentById(Long id) {
        return submitAssignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }
    public ResponseSubmit findSubmitById(Long id) {
        return submitAssignmentRepository.findById(id)
                .map(submitAssignment -> new ResponseSubmit(
                    submitAssignment.getId(),
                    submitAssignment.getTrack(),
                    submitAssignment.getAssignment().getId(),
                    submitAssignment.getWriter(),
                    submitAssignment.getSubmitStatus(),
                    submitAssignment.getPassNonePass()))
                .orElseThrow(InvalidIdException::new);
    }

    public ResponseAssignmentCount countAssignmentsByWriter(String writer) {
        int submittedTodayCount = submitAssignmentRepository.countByWriterAndAssignment_AssignmentStatusAndSubmitStatus(writer, AssignmentStatus.TODAY, SubmitStatus.SUBMITTED);
        int todayCount = assignmentService.countByAssignmentStatus(AssignmentStatus.TODAY);

        int submittedIngCount = submitAssignmentRepository.countByWriterAndAssignment_AssignmentStatusAndSubmitStatus(writer, AssignmentStatus.ING, SubmitStatus.SUBMITTED);
        int ingCount = assignmentService.countByAssignmentStatus(AssignmentStatus.ING);

        int doneCount = assignmentService.countByAssignmentStatus(AssignmentStatus.DONE);
        return new ResponseAssignmentCount(writer, submittedTodayCount, todayCount, submittedIngCount, ingCount, doneCount);
    }

    public List<ResponseAssignmentCount> countAssignmentsByTrack(TrackType track) {
        List<SubmitAssignment> assignments  = submitAssignmentRepository.findDistinctWriterByAssignment_Track(track); // 트랙에 해당하는 모든 작성자 목록을 가져옴
        List<String> writers = assignments.stream()
                .map(SubmitAssignment::getWriter)
                .distinct()
                .toList();
        List<ResponseAssignmentCount> responseList = new ArrayList<>();

        for (String writer : writers) {
            int submittedTodayCount = submitAssignmentRepository.countByAssignment_TrackAndWriterAndAssignment_AssignmentStatusAndSubmitStatus(track, writer, AssignmentStatus.TODAY, SubmitStatus.SUBMITTED);
            int todayCount = submitAssignmentRepository.countByAssignment_TrackAndWriterAndAssignment_AssignmentStatus(track, writer, AssignmentStatus.TODAY);

            int submittedIngCount = submitAssignmentRepository.countByAssignment_TrackAndWriterAndAssignment_AssignmentStatusAndSubmitStatus(track, writer, AssignmentStatus.ING, SubmitStatus.SUBMITTED);
            int ingCount = submitAssignmentRepository.countByAssignment_TrackAndWriterAndAssignment_AssignmentStatus(track, writer, AssignmentStatus.ING);

            int doneCount = submitAssignmentRepository.countByAssignment_TrackAndWriterAndAssignment_AssignmentStatus(track, writer, AssignmentStatus.DONE);

            responseList.add(new ResponseAssignmentCount(writer, submittedTodayCount, todayCount, submittedIngCount, ingCount, doneCount));
        }

        return responseList;
    }


    public List<SubmitAssignment> findAllSubmitAssignment() {
        return submitAssignmentRepository.findAll();
    }

    @Transactional
    public void deleteSubmitAssignment(Long id) {
        SubmitAssignment submitAssignment = submitAssignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        submitAssignmentRepository.delete(submitAssignment);
    }

    public ResponseAssignmentDetails getAssignmentDetailsByWriter(String writer) {
        int submittedTodayCount = submitAssignmentRepository.countByWriterAndAssignment_AssignmentStatusAndSubmitStatus(writer, AssignmentStatus.TODAY, SubmitStatus.SUBMITTED);
        int todayCount = assignmentService.countByAssignmentStatus(AssignmentStatus.TODAY);

        int submittedIngCount = submitAssignmentRepository.countByWriterAndAssignment_AssignmentStatusAndSubmitStatus(writer, AssignmentStatus.ING, SubmitStatus.SUBMITTED);
        int ingCount = assignmentService.countByAssignmentStatus(AssignmentStatus.ING);

        int doneCount = assignmentService.countByAssignmentStatus(AssignmentStatus.DONE);

        List<SubmitAssignment> todayAssignments = submitAssignmentRepository.findByWriterAndAssignment_AssignmentStatus(writer, AssignmentStatus.TODAY);
        List<SubmitAssignment> ingAssignments = submitAssignmentRepository.findByWriterAndAssignment_AssignmentStatus(writer, AssignmentStatus.ING);
        List<SubmitAssignment> doneAssignments = submitAssignmentRepository.findByWriterAndAssignment_AssignmentStatus(writer, AssignmentStatus.DONE);

        Map<String, List<SubmitAssignment>> assignments = new HashMap<>();
        assignments.put("today", todayAssignments);
        assignments.put("ing", ingAssignments);
        assignments.put("done", doneAssignments);

        return new ResponseAssignmentDetails(writer, submittedTodayCount, todayCount, submittedIngCount, ingCount, doneCount, assignments);
    }

}
