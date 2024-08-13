package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.dto.SubmitAssignmentDTO;
import likelion.sku_sku.service.AssignmentService;
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
@RequestMapping("/submit")
@Tag(name = "과제 관련")
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

    @Operation(summary = "(민규) 사람별 과제 현황 전체 조회", description = "Headers에 Bearer token 필요, 조회하고자 하는 아기사자 이름 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/all")
    public ResponseEntity<Map<String, List<SubmitAssignment>>> findAllAssignments(@RequestParam String writer) {
        Map<String, List<SubmitAssignment>> assignments = submitAssignmentService.findAllAssignmentsByWriter(writer);
        return ResponseEntity.ok(assignments);
    }

    @Operation(summary = "(민규) 트랙별 사람별 과제 상태별 완료한 개수, 총 개수 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/trackcnt")
    public ResponseEntity<List<ResponseAssignmentCount>> getAssignmentCountsByTrack(@RequestParam TrackType track) {
        return ResponseEntity.ok(submitAssignmentService.countAssignmentsByTrack(track));
    }

    @Operation(summary = "(민규) 과제 상태별 완료한 개수 및 전체 조회", description = "Headers에 Bearer token 필요, 조회하고자 하는 아기사자 이름 필요",
            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/details")
    public ResponseEntity<ResponseAssignmentDetails> getAssignmentDetails(@RequestParam String writer) {
        ResponseAssignmentDetails assignmentDetails = submitAssignmentService.getAssignmentDetailsByWriter(writer);
        return ResponseEntity.ok(assignmentDetails);
    }
    @GetMapping("/detail")
    public ResponseEntity<ResponseAssignmentDetails> getAssignmentDetailsByWriter(@RequestParam String writer) {
        ResponseAssignmentDetails response = submitAssignmentService.getAssignmentDetailsByWriter(writer);
        return ResponseEntity.ok(response);
    }
}
