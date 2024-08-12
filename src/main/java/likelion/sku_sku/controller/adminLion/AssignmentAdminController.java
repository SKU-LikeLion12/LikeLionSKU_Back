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

import java.io.IOException;

import static likelion.sku_sku.dto.AssignmentDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/assignment")
@Tag(name = "운영진 페이지: 과제 안내 관련")
public class AssignmentAdminController {
    private final AssignmentService assignmentService;

    @Operation(summary = "(민규) 과제 안내물 추가", description = "Headers에 Bearer token 필요, 과제 안내물의 trackType, title, description, body에 json 형태로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<?> addAssignment(@RequestBody createAssignmentRequest request) {
        Assignment assignment = assignmentService.addAssignment(
                request.getTrackType(),
                request.getTitle(),
                request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }

    @Operation(summary = "(민규) 과제 안내물 수정", description = "Headers에 Bearer token 필요, 과제 안내물의 id, trackType, title, description, body에 json 형태로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "수정 완료")})
    @PutMapping("/update")
    public ResponseEntity<?> updateAssignment(@RequestBody updateAssignmentRequest request) {
        Assignment assignment = assignmentService.updateAssignment(
                request.getId(),
                request.getTitle(),
                request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }

    @Operation(summary = "(민규) 과제 안내물 삭제", description = "Headers에 Bearer token 필요, 과제 안내물의 id 필요",
            responses = {@ApiResponse(responseCode = "200", description = "과제 안내물 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 과제 안내물 없")})
    @DeleteMapping("")
    public ResponseEntity<String> deleteAssignment(@RequestParam Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.ok("과제 안내물 삭제 성공");
    }
}
