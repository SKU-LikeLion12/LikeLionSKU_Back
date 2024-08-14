package likelion.sku_sku.controller.adminLion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.service.SubmitAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static likelion.sku_sku.dto.AssignmentDTO.AssignmentAll;
import static likelion.sku_sku.dto.SubmitAssignmentDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/submit")
@Tag(name = "관리자 기능: 과제 관리 관련")
public class SubmitAssignmentAdminController {
    private final SubmitAssignmentService submitAssignmentService;

//    @Operation(summary = "(민규) 제출한 과제 통과 여부 결정", description = "Headers에 Bearer token 필요, body에 json 형태로 제출한 과제 id 필요",
//            responses = {@ApiResponse(responseCode = "200", description = "통과 여부 변경 성공"),
//                    @ApiResponse(responseCode = "404", description = "그 id에 해당하는 값 없")})
//    @PutMapping("/decidePass")
//    public ResponseEntity<?> decidePassStatus(@RequestBody DecidePassStatusRequest request) {
//        SubmitAssignment submitAssignment = submitAssignmentService.decidePassStatus(request.getSubmitAssignmentId());
//        return ResponseEntity.ok(submitAssignment);
//    }
    @Operation(summary = "(민규) 사람별 과제 현황 전체 조회", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 조회하고자 하는 아기사자 이름 필요",
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

    @Operation(summary = "(민규) 과제 상태별 완료한 개수 및 전체 조회", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 조회하고자 하는 아기사자 이름 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/details")
    public ResponseEntity<ResponseAssignmentDetails> getAssignmentDetailsByWriter(@ModelAttribute WriterAndTrack request) {
        ResponseAssignmentDetails response = submitAssignmentService.getAssignmentDetailsByWriter(request.getWriter(), request.getTrack());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "(민규) 제출한 과제 개별 조회", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 과제 안내물 id, 제출한 과제 작성자 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "그 제출한 과제 없")})
    @GetMapping("/assignment")
    public ResponseEntity<AssignmentAll> getAssignmentWithSubmissions(@ModelAttribute assignmentWriter request) {
        AssignmentAll assignmentAll = submitAssignmentService.getAssignmentWithSubmissions(request.getAssignmentId(), request.getWriter());
        return ResponseEntity.ok(assignmentAll);
    }
}
