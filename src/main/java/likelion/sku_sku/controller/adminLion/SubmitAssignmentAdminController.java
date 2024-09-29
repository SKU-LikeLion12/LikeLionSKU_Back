package likelion.sku_sku.controller.adminLion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.enums.TrackType;
import likelion.sku_sku.service.SubmitAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static likelion.sku_sku.dto.AssignmentDTO.AssignmentAll;
import static likelion.sku_sku.dto.SubmitAssignmentDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/submit")
@Tag(name = "관리자 기능: 과제 관리 관련")
public class SubmitAssignmentAdminController {
    private final SubmitAssignmentService submitAssignmentService;

    @Operation(summary = "(민규) 트랙별 사람별 과제 상태별 완료한 개수, 총 개수 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/trackcnt")
    public ResponseEntity<ResponseAssignmentSummary> getAssignmentCountsByTrack(@RequestParam TrackType track) {
        return ResponseEntity.ok(submitAssignmentService.countAssignmentsByTrack(track));
    }

    @Operation(summary = "(민규) 제출한 과제 개별 조회", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 과제 안내물 id, 제출한 과제 작성자 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "그 제출한 과제 없")})
    @GetMapping("/assignment")
    public ResponseEntity<AssignmentAll> getAssignmentWithSubmissions(@ModelAttribute assignmentWriter request) {
        AssignmentAll assignmentAll = submitAssignmentService.getAssignmentWithSubmissions(request.getAssignmentId(), request.getWriter());
        return ResponseEntity.ok(assignmentAll);
    }

    @Operation(summary = "(민규) 과제 안내물 상태별 조회 및 과제 조회", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 조회하고자 하는 아기사자 이름, 트랙 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회 성공")})
    @GetMapping("/status")
    public ResponseEntity<AssignmentStatusGrouped> getAssignmentsByWriterAndTrack(@ModelAttribute WriterAndTrack request) {
        AssignmentStatusGrouped assignmentsSummary = submitAssignmentService.findAssignmentsByWriterAndTrackGroupedByStatus(request.getWriter(), request.getTrack());
        return ResponseEntity.ok(assignmentsSummary);
    }
}
