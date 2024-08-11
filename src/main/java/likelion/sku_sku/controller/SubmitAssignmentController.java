package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import likelion.sku_sku.domain.SubmitAssignment;
import likelion.sku_sku.dto.SubmitAssignmentDTO;
import likelion.sku_sku.service.SubmitAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

import static likelion.sku_sku.dto.SubmitAssignmentDTO.*;

@RestController
@RequiredArgsConstructor
public class SubmitAssignmentController {
    private final SubmitAssignmentService submitAssignmentService;

    @Operation(summary = "(민규) 과제 제출", description = "Headers에 Bearer token 필요, 강의의 trackType, title, 강의자료 필요, body에 form-data로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<SubmitAssignment> createSubmitAssignment(@RequestHeader("Authorization") String bearer, CreateSubmitRequest request) throws IOException {
            SubmitAssignment submitAssignment = submitAssignmentService.createSubmitAssignment(bearer, request.getAssignmentId(), request.getFiles());
            return ResponseEntity.status(HttpStatus.CREATED).body(submitAssignment);
    }

    @Operation(summary = "(민규) 과제파일 수정", description = "Headers에 Bearer token 필요, 강의 ID와 수정할 정보를 body에 form-data로 포함시켜야 함",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 Id의 강의를 찾을 수 없음")})
    @PutMapping("/update")
    public ResponseEntity<SubmitAssignment> updateSubmitAssignment(UpdateSubmitRequest request) throws IOException {
            SubmitAssignment submitAssignment = submitAssignmentService.updateSubmitAssignment(request.getSubmitAssignmentId(), request.getFiles());
            return ResponseEntity.ok(submitAssignment);
    }
}
