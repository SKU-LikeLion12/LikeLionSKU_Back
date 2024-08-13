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

import static likelion.sku_sku.dto.SubmitAssignmentDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/submit")
@Tag(name = "관리자 기능: 과제 관련")
public class SubmitAssignmentAdminController {
    private final SubmitAssignmentService submitAssignmentService;

    @Operation(summary = "(민규) 과제 통과 여부 결정", description = "Headers에 Bearer token 필요, 과제 id 필요",
            responses = {@ApiResponse(responseCode = "200", description = "통과 여부 변경 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @PutMapping("/decidePass")
    public ResponseEntity<?> decidePassStatus(@RequestBody DecidePassStatusRequest request) {
        SubmitAssignment submitAssignment = submitAssignmentService.decidePassStatus(request.getSubmitAssignmentId());
        return ResponseEntity.ok(submitAssignment);
    }

//    @Operation(summary = "(민규) 과제 상태별 완료한 개수, 총 개수 조회", description = "Headers에 Bearer token 필요",
//            responses = {@ApiResponse(responseCode = "201", description = "조회 성공"),
//                    @ApiResponse(responseCode = "404", description = "")})
//    @GetMapping("/counts")
//    public ResponseEntity<ResponseAssignmentCount> getAssignmentCounts(@RequestParam String writer) {
//        return ResponseEntity.ok(submitAssignmentService.countAssignmentsByWriter(writer));
//    }
}
