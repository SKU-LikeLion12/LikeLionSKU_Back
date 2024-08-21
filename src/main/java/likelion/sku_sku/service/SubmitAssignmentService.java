package likelion.sku_sku.service;

import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.domain.Feedback;
import likelion.sku_sku.domain.JoinAssignmentFiles;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.SubmitStatus;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.exception.InvalidSubmitAssignmentException;
import likelion.sku_sku.repository.SubmitAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static likelion.sku_sku.dto.AssignmentDTO.*;
import static likelion.sku_sku.dto.FeedbackDTO.ResponseFeedback;
import static likelion.sku_sku.dto.JoinAssignmentFilesDTO.ResponseJoinAss;
import static likelion.sku_sku.dto.SubmitAssignmentDTO.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmitAssignmentService {
    private final SubmitAssignmentRepository submitAssignmentRepository;
    private final LionService lionService;
    private final JoinAssignmentFilesService joinAssignmentFilesService;
    private final FeedbackService feedbackService;
    private final AssignmentService assignmentService;


    @Transactional // 과제 제출
    public ResponseEntity<SubmitAssignment> createSubmitAssignment(String bearer, Long assignmentId, List<MultipartFile> files) throws IOException {
        String writer = lionService.tokenToLionName(bearer.substring(7));
        Optional<SubmitAssignment> existSumnitAssignment = submitAssignmentRepository.findByWriterAndAssignment_Id(writer, assignmentId);
        if (existSumnitAssignment.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(existSumnitAssignment.get());
        }
        Assignment assignment = assignmentService.findAssignmentById(assignmentId);
        SubmitAssignment submitAssignment = new SubmitAssignment(assignment.getTrack(), assignment, writer);
        submitAssignmentRepository.save(submitAssignment);

        joinAssignmentFilesService.uploadJoinAssignmentFiles(submitAssignment, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(submitAssignment);
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
    public List<SubmitAssignment> findByAssignmentId(Long assignmentId) {
        return submitAssignmentRepository.findByAssignmentId(assignmentId);
    }

    public Optional<SubmitAssignment> findByWriterAndAssignment_Id(String writer, Long assignmentId) {
        return submitAssignmentRepository.findByWriterAndAssignment_Id(writer, assignmentId);
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

    public ResponseAssignmentSummary countAssignmentsByTrack(TrackType track) {
        List<SubmitAssignment> assignments = submitAssignmentRepository.findDistinctWriterByAssignment_Track(track);
        List<String> writers = assignments.stream()
                .map(SubmitAssignment::getWriter)
                .distinct()
                .toList();

        List<ResponseAssignmentCount> responseList = new ArrayList<>();
        int totalAssignmentsByTrack = getTotalAssignmentsByTrack(track);

        for (String writer : writers) {
            int totalTodayAssignments = assignmentService.countByAssignmentStatusAndTrack(AssignmentStatus.TODAY, track);

            int submittedCount = submitAssignmentRepository.countByWriterAndAssignment_Track(writer, track);
            int passCount = submitAssignmentRepository.countByWriterAndAssignment_TrackAndPassNonePass(writer, track, PassNonePass.PASS);
            int unsubmittedCount = totalTodayAssignments - submittedCount;

            responseList.add(new ResponseAssignmentCount(writer, unsubmittedCount, submittedCount, passCount));
        }

        return new ResponseAssignmentSummary(totalAssignmentsByTrack, responseList);
    }


    private int getTotalAssignmentsByTrack(TrackType track) {
        return assignmentService.countByTrack(track);
    }

    @Transactional
    public void deleteSubmitAssignment(Long id) {
        SubmitAssignment submitAssignment = submitAssignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
        submitAssignmentRepository.delete(submitAssignment);
    }

    // @GetMapping("/admin/submit/details")
    public ResponseAssignmentDetails getAssignmentDetailsByWriter(String writer, TrackType track) {
        int submittedTodayCount = getSubmittedCountByStatus(writer, AssignmentStatus.TODAY, track);
        int todayCount = getCountByStatusAndTrack(AssignmentStatus.TODAY, track);

        int submittedIngCount = getSubmittedCountByStatus(writer, AssignmentStatus.ING, track);
        int ingCount = getCountByStatusAndTrack(AssignmentStatus.ING, track);

        int doneCount = getCountByStatusAndTrack(AssignmentStatus.DONE, track);

        Map<String, List<AssignmentAllDTO>> assignments = new HashMap<>();
        assignments.put("today", convertAssignmentsToDTOByTrack(AssignmentStatus.TODAY, writer, track));
        assignments.put("ing", convertAssignmentsToDTOByTrack(AssignmentStatus.ING, writer, track));
        assignments.put("done", convertAssignmentsToDTOByTrack(AssignmentStatus.DONE, writer, track));

        return new ResponseAssignmentDetails(writer, submittedTodayCount, todayCount, submittedIngCount, ingCount, doneCount, assignments);
    }

    private int getSubmittedCountByStatus(String writer, AssignmentStatus status, TrackType trackType) {
        return submitAssignmentRepository.countByWriterAndAssignment_AssignmentStatusAndAssignment_TrackAndSubmitStatus(writer, status, trackType, SubmitStatus.SUBMITTED);
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

    // @GetMapping("/admin/submit/assignment")
    public AssignmentAll getAssignmentWithSubmissions(Long assignmentId, String writer) {
        Assignment assignment = assignmentService.findAssignmentById(assignmentId);

        SubmitAssignment submitAssignment = submitAssignmentRepository.findByWriterAndAssignment(writer, assignment)
                .orElseThrow(InvalidSubmitAssignmentException::new);

        List<ResponseJoinAss> filesDTO = joinAssignmentFilesService.findBySubmitAssignment(submitAssignment)
                .stream()
                .map(ResponseJoinAss::new)
                .toList();

        Feedback feedback = feedbackService.findFeedbackBySubmitAssignment(submitAssignment)
                .orElse(null);

        ResponseFeedback responseFeedback = (feedback != null) ? new ResponseFeedback(feedback) : null;

        AssignSubmitFeed assignSubmitFeed = new AssignSubmitFeed(submitAssignment, filesDTO, responseFeedback);

        return new AssignmentAll(assignment, assignSubmitFeed);
    }

    // @GetMapping("/admin/submit/status")
    public AssignmentStatusGroupedDTO findAssignmentsByWriterAndTrackGroupedByStatus(String writer, TrackType track) {
        List<Assignment> assignments = assignmentService.findByTrackOrderByIdDesc(track);

        List<AssignmentStatusDTO> todayAssignments = new ArrayList<>();
        List<AssignmentStatusDTO> ingAssignments = new ArrayList<>();
        List<AssignmentStatusDTO> doneAssignments = new ArrayList<>();

        for (Assignment assignment : assignments) {
            AssignmentStatus status = adminDetermineStatus(assignment, writer);
            AssignmentStatusDTO assignmentStatusDTO = createAssignmentStatusDTO(assignment, status, writer);

            switch (status) {
                case TODAY -> todayAssignments.add(assignmentStatusDTO);
                case ING -> ingAssignments.add(assignmentStatusDTO);
                case DONE -> doneAssignments.add(assignmentStatusDTO);
            }
        }

        return new AssignmentStatusGroupedDTO(
                writer,
                todayAssignments.size(), todayAssignments,
                ingAssignments.size(), ingAssignments,
                doneAssignments.size(), doneAssignments
        );
    }

    private AssignmentStatus adminDetermineStatus(Assignment assignment, String writer) {
        if (assignment.getAssignmentStatus() == AssignmentStatus.DONE) {
            return AssignmentStatus.DONE;
        }
        SubmitAssignment submitAssignment = submitAssignmentRepository.findByWriterAndAssignment(writer, assignment).orElse(null);
        return (submitAssignment != null) ? submitAssignment.getStatusAssignment() : AssignmentStatus.TODAY;
    }

    private AssignmentStatusDTO createAssignmentStatusDTO(Assignment assignment, AssignmentStatus status, String writer) {
        SubmitAssignment submitAssignment = submitAssignmentRepository.findByWriterAndAssignment(writer, assignment).orElse(null);
        List<JoinAssignmentFiles> files = (submitAssignment != null) ? joinAssignmentFilesService.findBySubmitAssignment(submitAssignment) : new ArrayList<>();

        SubmitAssignmentWithoutDTO submitAssignmentWithoutDTO = (submitAssignment != null) ?
                new SubmitAssignmentWithoutDTO(
                        submitAssignment,
                        files,
                        feedbackService.findFeedbackBySubmitAssignment(submitAssignment).map(ResponseFeedback::new).orElse(null)
                )
                : null;

        return new AssignmentStatusDTO(assignment, status, submitAssignmentWithoutDTO);
    }

    // @GetMapping("/submit/status")
    public AssignmentStatusGroupedDTO assignmentsByWriterAndTrackGroupedByStatus(String writer, TrackType track) {
        List<Assignment> assignments = assignmentService.findByTrackOrderByIdDesc(track);

        List<AssignmentStatusDTO> todayAssignments = new ArrayList<>();
        List<AssignmentStatusDTO> ingAssignments = new ArrayList<>();
        List<AssignmentStatusDTO> doneAssignments = new ArrayList<>();

        for (Assignment assignment : assignments) {
            AssignmentStatus status = determineStatus(assignment, writer);
            AssignmentStatusDTO assignmentStatusDTO = createAssignmentStatusDTO(assignment, status, writer);

            switch (status) {
                case TODAY -> todayAssignments.add(assignmentStatusDTO);
                case ING -> ingAssignments.add(assignmentStatusDTO);
                case DONE -> doneAssignments.add(assignmentStatusDTO);
            }
        }

        return new AssignmentStatusGroupedDTO(
                writer,
                todayAssignments.size(), todayAssignments,
                ingAssignments.size(), ingAssignments,
                doneAssignments.size(), doneAssignments
        );
    }

    private AssignmentStatus determineStatus(Assignment assignment, String writer) {
        SubmitAssignment submitAssignment = submitAssignmentRepository.findByWriterAndAssignment(writer, assignment).orElse(null);

        if (submitAssignment == null) {
            return AssignmentStatus.TODAY;
        }
        if (submitAssignment.getStatusAssignment() == AssignmentStatus.ING) {
            return AssignmentStatus.ING;
        } else if (submitAssignment.getStatusAssignment() == AssignmentStatus.DONE) {
            return AssignmentStatus.DONE;
        }
        return AssignmentStatus.TODAY;
    }

}
