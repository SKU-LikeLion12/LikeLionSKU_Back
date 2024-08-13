package likelion.sku_sku.controller.adminLion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.service.SubmitAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static likelion.sku_sku.dto.SubmitAssignmentDTO.DecidePassStatusRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/submit")
@Tag(name = "관리자 기능: 과제 관리 관련")
public class SubmitAssignmentAdminController {
    private final SubmitAssignmentService submitAssignmentService;

    @Operation(summary = "(민규) 제출한 과제 통과 여부 결정", description = "Headers에 Bearer token 필요, 제출한 과제 id 필요",
            responses = {@ApiResponse(responseCode = "200", description = "통과 여부 변경 성공"),
                    @ApiResponse(responseCode = "404", description = "그 id에 해당하는 값 없")})
    @PutMapping("/decidePass")
    public ResponseEntity<?> decidePassStatus(@RequestBody DecidePassStatusRequest request) {
        SubmitAssignment submitAssignment = submitAssignmentService.decidePassStatus(request.getSubmitAssignmentId());
        return ResponseEntity.ok(submitAssignment);
    }
}
