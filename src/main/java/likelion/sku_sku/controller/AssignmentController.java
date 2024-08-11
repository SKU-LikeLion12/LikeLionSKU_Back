package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @Operation(summary = "(민규) 과제 안내 추가", description = "Headers에 Bearer token 필요, 과제의 trackType, title, description, body에 json 형태로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<?> uploadFiles(@RequestBody createAssignmentRequest request) throws IOException {
        Assignment assignment = assignmentService.addAssignment(
                request.getTrackType(),
                request.getTitle(),
                request.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
    }
}
