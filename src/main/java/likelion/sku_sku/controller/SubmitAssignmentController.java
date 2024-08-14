package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.service.SubmitAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static likelion.sku_sku.dto.SubmitAssignmentDTO.CreateSubmitRequest;
import static likelion.sku_sku.dto.SubmitAssignmentDTO.UpdateSubmitRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submit")
@Tag(name = "과제 관련")
public class SubmitAssignmentController {
    private final SubmitAssignmentService submitAssignmentService;
    @Operation(summary = "(민규) 과제 제출", description = "Headers에 Bearer token 필요, body에 form-data로 강의 안내물의 id 필요",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<SubmitAssignment> createSubmitAssignment(@RequestHeader("Authorization") String bearer, CreateSubmitRequest request) throws IOException {
        SubmitAssignment submitAssignment = submitAssignmentService.createSubmitAssignment(bearer, request.getAssignmentId(), request.getFiles());
        return ResponseEntity.status(HttpStatus.CREATED).body(submitAssignment);
    }

    @Operation(summary = "(민규) 과제파일 수정", description = "Headers에 Bearer token 필요, body에 form-data로 제출한 과제의 id와 수정할 파일 필요",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 Id의 강의를 찾을 수 없음")})
    @PutMapping("/update")
    public ResponseEntity<SubmitAssignment> updateSubmitAssignment(UpdateSubmitRequest request) throws IOException {
        SubmitAssignment submitAssignment = submitAssignmentService.updateSubmitAssignment(request.getSubmitAssignmentId(), request.getFiles());
        return ResponseEntity.ok(submitAssignment);
    }


}
