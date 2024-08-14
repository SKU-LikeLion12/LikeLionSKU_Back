package likelion.sku_sku.service;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.JoinAssignmentFiles;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
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

import static likelion.sku_sku.dto.AssignmentDTO.*;
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

    @Transactional
    public void deleteSubmitAssignment(Long id) {
        SubmitAssignment submitAssignment = submitAssignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        submitAssignmentRepository.delete(submitAssignment);

    }public ResponseAssignmentDetails getAssignmentDetailsByWriter(String writer, TrackType track) {
        int submittedTodayCount = getSubmittedCountByStatus(writer, AssignmentStatus.TODAY);
        int todayCount = getCountByStatusAndTrack(AssignmentStatus.TODAY, track);

        int submittedIngCount = getSubmittedCountByStatus(writer, AssignmentStatus.ING);
        int ingCount = getCountByStatusAndTrack(AssignmentStatus.ING, track);

        int doneCount = getCountByStatusAndTrack(AssignmentStatus.DONE, track);

        Map<String, List<AssignmentAllDTO>> assignments = new HashMap<>();
        assignments.put("today", convertAssignmentsToDTOByTrack(AssignmentStatus.TODAY, writer, track));
        assignments.put("ing", convertAssignmentsToDTOByTrack(AssignmentStatus.ING, writer, track));
        assignments.put("done", convertAssignmentsToDTOByTrack(AssignmentStatus.DONE, writer, track));

        return new ResponseAssignmentDetails(writer, submittedTodayCount, todayCount, submittedIngCount, ingCount, doneCount, assignments);
    }

    private int getSubmittedCountByStatus(String writer, AssignmentStatus status) {
        return submitAssignmentRepository.countByWriterAndAssignment_AssignmentStatusAndSubmitStatus(writer, status, SubmitStatus.SUBMITTED);
    }
    private int getCountByStatusAndTrack(AssignmentStatus status, TrackType track) {
        return assignmentService.countByAssignmentStatusAndTrack(status, track);
    }

    private List<AssignmentAllDTO> convertAssignmentsToDTOByTrack(AssignmentStatus status, String writer, TrackType track) {
        List<Assignment> assignments = assignmentService.findAssignmentsByAssignmentStatusAndTrack(status, track);
        List<AssignmentAllDTO> dtoList = new ArrayList<>();

        for (Assignment assignment : assignments) {
            SubmitAssignment submitAssignment = submitAssignmentRepository.findByWriterAndAssignment(writer, assignment)
                    .orElse(null);
            List<JoinAssignmentFiles> files = submitAssignment != null ? joinAssignmentFilesService.findBySubmitAssignment(submitAssignment) : new ArrayList<>();
            SubmitAssignmentAllDTO submitAssignmentAllDTO = submitAssignment != null ? new SubmitAssignmentAllDTO(submitAssignment, files) : null;
            AssignmentAllDTO dto = new AssignmentAllDTO(assignment, submitAssignmentAllDTO);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
