package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Assignment;
import likelion.sku_sku.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static likelion.sku_sku.dto.AssignmentDTO.FindTrackStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignment")
@Tag(name = "과제 안내 관련")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @Operation(summary = "(민규) 과제 안내 트랙별 && 상태별 조회", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 track, status 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "아무것도 없")})
    @GetMapping("") // 트랙과 과제 상태를 넣으면 그에 알맞는 결과가 나옴
    public ResponseEntity<?> getAssignments(@ModelAttribute FindTrackStatus request) {
        Map<String, Object> response = assignmentService.getAssignmentsAndCountByTrackAndStatus(request.getTrack(), request.getStatus());
        int assignmentCount = (int) response.get("count");

        if (assignmentCount == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아무것도 없");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
