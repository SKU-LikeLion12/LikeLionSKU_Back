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

    // @PostMapping("/submit/add")
    @Transactional // 과제 제출
    public ResponseEntity<SubmitAssignment> createSubmitAssignment(String bearer, Long assignmentId, List<MultipartFile> files) throws IOException {
        String writer = lionService.tokenToLionName(bearer.substring(7));
        Optional<SubmitAssignment> existSubmitAssignment = submitAssignmentRepository.findByWriterAndAssignment_Id(writer, assignmentId);
        if (existSubmitAssignment.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(existSubmitAssignment.get());
        }
        Assignment assignment = assignmentService.findAssignmentById(assignmentId);
        SubmitAssignment submitAssignment = new SubmitAssignment(assignment.getTrack(), assignment, writer);
        submitAssignmentRepository.save(submitAssignment);

        joinAssignmentFilesService.uploadJoinAssignmentFiles(submitAssignment, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(submitAssignment);
    }

    // @PutMapping("/submit/update")
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

    public SubmitAssignment findSubmitAssignmentById(Long id) {
        return submitAssignmentRepository.findById(id)
                .orElseThrow(InvalidIdException::new);
    }

    // @GetMapping("/admin/submit/trackcnt")
    public ResponseAssignmentSummary countAssignmentsByTrack(TrackType track) {
        List<String> allWritersInTrack = lionService.findWritersByTrack(track);
//        List<String> allWritersInTrackAndBaby = lionService.findWritersByTrackAndBaby(track);

//        List<SubmitAssignment> assignments = submitAssignmentRepository.findDistinctWriterByAssignment_Track(track);
//        List<String> submittedWriters = assignments.stream()
//                .map(SubmitAssignment::getWriter)
//                .distinct()
//                .toList();

        List<ResponseAssignmentCount> responseList = new ArrayList<>();
        int totalAssignmentsByTrack = getTotalAssignmentsByTrack(track);

        for (String writer : allWritersInTrack) {
            int totalTodayAssignments = assignmentService.countByAssignmentStatusAndTrack(AssignmentStatus.TODAY, track);

            int submittedCount = submitAssignmentRepository.countByWriterAndAssignment_TrackAndPassNonePass(writer, track, PassNonePass.FAIL);
            int passCount = submitAssignmentRepository.countByWriterAndAssignment_TrackAndPassNonePass(writer, track, PassNonePass.PASS);
            int unsubmittedCount = totalTodayAssignments - submittedCount - passCount;

            responseList.add(new ResponseAssignmentCount(writer, unsubmittedCount, submittedCount, passCount));
        }

        return new ResponseAssignmentSummary(totalAssignmentsByTrack, responseList);
    }

    private int getTotalAssignmentsByTrack(TrackType track) {
        return assignmentService.countByTrack(track);
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
    public AssignmentStatusGrouped findAssignmentsByWriterAndTrackGroupedByStatus(String writer, TrackType track) {
        List<Assignment> assignments = assignmentService.findByTrackOrderByIdDesc(track);

        List<AssignmentStatusDTO> todayAssignments = new ArrayList<>();
        List<AssignmentStatusDTO> ingAssignments = new ArrayList<>();

        for (Assignment assignment : assignments) {
            AssignmentStatus status = adminDetermineStatus(assignment, writer);
            AssignmentStatusDTO assignmentStatusDTO = createAssignmentStatusDTO(assignment, status, writer);

            switch (status) {
                case TODAY -> todayAssignments.add(assignmentStatusDTO);
                case ING -> ingAssignments.add(assignmentStatusDTO);
            }
        }

        int submittedCount = submitAssignmentRepository.countByWriterAndAssignment_TrackAndPassNonePass(writer, track, PassNonePass.FAIL);
        int passCount = submitAssignmentRepository.countByWriterAndAssignment_TrackAndPassNonePass(writer, track, PassNonePass.PASS);

        return new AssignmentStatusGrouped(
                writer, submittedCount, passCount,
                todayAssignments.size(), todayAssignments,
                ingAssignments.size(), ingAssignments
//                doneAssignments.size(), doneAssignments
        );
    }

    private AssignmentStatus adminDetermineStatus(Assignment assignment, String writer) {
        SubmitAssignment submitAssignment = submitAssignmentRepository.findByWriterAndAssignment(writer, assignment).orElse(null);

        if (submitAssignment == null) {
            return AssignmentStatus.TODAY;
        } else if (submitAssignment.getStatusAssignment() == AssignmentStatus.ING || submitAssignment.getStatusAssignment() == AssignmentStatus.DONE) {
            return AssignmentStatus.ING;
        }
        return AssignmentStatus.TODAY;
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
