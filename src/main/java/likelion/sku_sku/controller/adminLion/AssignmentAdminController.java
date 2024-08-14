package likelion.sku_sku.controller.adminLion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static likelion.sku_sku.dto.AssignmentDTO.createAssignmentRequest;
import static likelion.sku_sku.dto.AssignmentDTO.updateAssignmentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/assignment")
@Tag(name = "관리자 기능: 과제 안내물 관련")
public class AssignmentAdminController {
    private final AssignmentService assignmentService;

    @Operation(summary = "(민규) 과제 안내물 추가", description = "Headers에 Bearer token 필요, body에 json 형태로 과제 안내물의 trackType, title, subTitile, description",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<?> addAssignment(@RequestBody createAssignmentRequest request) {
        Assignment assignment = assignmentService.addAssignment(
                request.getTrackType(),
                request.getTitle(),
                request.getSubtitle(),
                request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }

    @Operation(summary = "(민규) 과제 안내물 수정", description = "Headers에 Bearer token 필요, body에 json 형태로 과제 안내물의 id와 수정하고 싶은 값만 넣으면 됨",
            responses = {@ApiResponse(responseCode = "201", description = "수정 완료")})
    @PutMapping("/update")
    public ResponseEntity<?> updateAssignment(@RequestBody updateAssignmentRequest request) {
        Assignment assignment = assignmentService.updateAssignment(
                request.getId(),
                request.getTitle(),
                request.getSubtitle(),
                request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }

    @Operation(summary = "(민규) 과제 안내물 삭제", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 과제 안내물의 id 필요",
            responses = {@ApiResponse(responseCode = "200", description = "과제 안내물 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "그 id에 해당하는 값 없")})
    @DeleteMapping("")
    public ResponseEntity<String> deleteAssignment(@RequestParam Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.ok("과제 안내물 삭제 성공");
    }
}
