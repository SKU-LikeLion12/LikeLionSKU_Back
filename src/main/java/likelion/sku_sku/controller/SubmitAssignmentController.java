package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.AssignmentStatus;
import likelion.sku_sku.domain.enums.PassNonePass;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.service.SubmitAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import static likelion.sku_sku.dto.SubmitAssignmentDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submitassignment")
public class SubmitAssignmentController {
    private final SubmitAssignmentService submitAssignmentService;

    @Operation(summary = "(민규) 과제 제출", description = "Headers에 Bearer token 필요, assignmentId, 과제 파일 필요, body에 form-data로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<SubmitAssignment> createSubmitAssignment(@RequestHeader("Authorization") String bearer, CreateSubmitRequest request) throws IOException {
            SubmitAssignment submitAssignment = submitAssignmentService.createSubmitAssignment(bearer, request.getAssignmentId(), request.getFiles());
            return ResponseEntity.status(HttpStatus.CREATED).body(submitAssignment);
    }

    @Operation(summary = "(민규) 과제파일 수정", description = "Headers에 Bearer token 필요, submitAssignmentId Id와 수정할 파일을 body에 form-data로 포함시켜야 함",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 Id의 강의를 찾을 수 없음")})
    @PutMapping("/update")
    public ResponseEntity<SubmitAssignment> updateSubmitAssignment(UpdateSubmitRequest request) throws IOException {
            SubmitAssignment submitAssignment = submitAssignmentService.updateSubmitAssignment(request.getSubmitAssignmentId(), request.getFiles());
            return ResponseEntity.ok(submitAssignment);
    }

    @Operation(summary = "(민규) 과제 통과 여부 결정", description = "Headers에 Bearer token 필요, 과제 id 필요",
            responses = {@ApiResponse(responseCode = "200", description = "통과 여부 변경 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @PutMapping("/decidePass")
    public ResponseEntity<?> decidePassStatus(@RequestBody DecidePassStatusRequest request) {
        SubmitAssignment submitAssignment = submitAssignmentService.decidePassStatus(request.getSubmitAssignmentId());
        return ResponseEntity.ok(submitAssignment);
    }


    @Operation(summary = "(민규) 과제 현황 조회", description = "Headers에 Bearer token 필요, 조회하고자 하는 아기사자 이름 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("")
    public ResponseEntity<List<SubmitAssignment>> findAssignments(@RequestParam String writer) {
        return ResponseEntity.ok(submitAssignmentService.findAssignmentsByWriter(writer));
    }

    @Operation(summary = "(민규) 과제 현황 전체 조회", description = "Headers에 Bearer token 필요, 조회하고자 하는 아기사자 이름 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/all")
    public ResponseEntity<Map<String, List<SubmitAssignment>>> findAllAssignments(@RequestParam String writer) {
        Map<String, List<SubmitAssignment>> assignments = submitAssignmentService.findAllAssignmentsByWriter(writer);
        return ResponseEntity.ok(assignments);
    }

    @Operation(summary = "(민규) 오늘의 과제 현황 조회", description = "Headers에 Bearer token 필요, 조회하고자 하는 아기사자 이름 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/today")
    public ResponseEntity<List<SubmitAssignment>> findTodayAssignments(@RequestParam String writer) {
        return ResponseEntity.ok(submitAssignmentService.findAssignmentsByWriterAndStatus(writer, AssignmentStatus.TODAY));
    }

    @Operation(summary = "(민규) 진행중인 과제 현황 조회", description = "Headers에 Bearer token 필요, 조회하고자 하는 아기사자 이름 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/ing")
    public ResponseEntity<List<SubmitAssignment>> findIngAssignments(@RequestParam String writer) {
        return ResponseEntity.ok(submitAssignmentService.findAssignmentsByWriterAndStatus(writer, AssignmentStatus.ING));
    }


    @Operation(summary = "(민규) 완료된 과제 조회", description = "Headers에 Bearer token 필요, 조회하고자 하는 아기사자 이름 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/done")
    public ResponseEntity<List<SubmitAssignment>> findDoneAssignments(@RequestParam String writer) {
        return ResponseEntity.ok(submitAssignmentService.findAssignmentsByWriterAndStatus(writer, AssignmentStatus.DONE));
    }

    @Operation(summary = "(민규) backend 과제 관리 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/backend")
    public ResponseEntity<List<SubmitAssignment>> findBackendAssignments() {
        return ResponseEntity.ok(submitAssignmentService.findAssignmentsByTrackType(TrackType.BACKEND));
    }


    @Operation(summary = "(민규) frontend 과제 관리 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/frontend")
    public ResponseEntity<List<SubmitAssignment>> findFrontendAssignments() {
        return ResponseEntity.ok(submitAssignmentService.findAssignmentsByTrackType(TrackType.FRONTEND));
    }

    @Operation(summary = "(민규) pm-design 과제 관리 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/pm-design")
    public ResponseEntity<List<SubmitAssignment>> findPmDesignAssignments() {
        return ResponseEntity.ok(submitAssignmentService.findAssignmentsByTrackType(TrackType.PM_DESIGN));
    }

    @Operation(summary = "(민규) 과제 상태별 완료한 개수, 총 개수 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/counts")
    public ResponseEntity<ResponseAssignmentCount> getAssignmentCounts(@RequestParam String writer) {
        return ResponseEntity.ok(submitAssignmentService.countAssignmentsByWriter(writer));
    }


}
