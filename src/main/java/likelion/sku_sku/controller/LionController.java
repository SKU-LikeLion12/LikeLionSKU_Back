package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.exception.IllegalEmailException;
import likelion.sku_sku.exception.IllegalLionIdException;
import likelion.sku_sku.exception.InvalidRoleException;
import likelion.sku_sku.service.LionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static likelion.sku_sku.dto.LionDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lion")
@PreAuthorize("hasRole('ADMIN_LION')")
@Tag(name = "관리자 페이지: Lion 관련")
public class LionController {
    private final LionService lionService;

    @Operation(summary = "(민규) Lion 추가", description = "Headers에 Bearer token 필요, Lion의 name, email, role 필요",
            responses = {@ApiResponse(responseCode = "201", description = "Lion 생성 성공, Lion 객체 반환"),
                    @ApiResponse(responseCode = "409", description = "그 email 이미 있")})
    @PostMapping("/add")
    public ResponseEntity<?> addLion(LionCreateRequest request) {
        try {
            Lion lion = lionService.addLion(
                    request.getName(),
                    request.getEmail(),
                    request.getRoleType());
            return ResponseEntity.status(HttpStatus.CREATED).body(lion);
        } catch (InvalidRoleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalEmailException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @Operation(summary = "(민규) Lion 수정", description = "Headers에 Bearer token 필요, Lion의 id, name, email, role 필요",
            responses = {@ApiResponse(responseCode = "201", description = "Lion 수정 성공, 수정된 Lion 객체 반환"),
                    @ApiResponse(responseCode = "409", description = "그 email 이미 있"),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 Lion 없")})
    @PutMapping("/update")
    public ResponseEntity<?> updateLion(LionUpdateRequest request) {
        try {
            Lion lion = lionService.updateLion(
                    request.getId(),
                    request.getName(),
                    request.getEmail(),
                    request.getRoleType());
            return ResponseEntity.status(HttpStatus.CREATED).body(lion);
        } catch (IllegalLionIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (InvalidRoleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalEmailException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @Operation(summary = "(민규) id로 Lion 개별 정보 조회", description = "Headers에 Bearer token 필요, Lion의 id 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회를 하면 Lion 이름, 이메일, 역할이 출력."),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 Lion 없")})
    @GetMapping("/{id}")
        public ResponseEntity<ResponseLionUpdate> findLionById(@PathVariable("id") Long id) {
        ResponseLionUpdate responseLion = lionService.findLionById(id);
        if (responseLion != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responseLion);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "(민규) 모든 Lion에 대한 정보 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "200", description = "모든 Lion 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "Lion이 한명도 없")})
    @GetMapping("/all")
    public ResponseEntity<List<Lion>> getAllLions() {
        List<Lion> lions = lionService.getAllLions();
        if (lions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(lions);
    }

    @Operation(summary = "(민규) Lion 삭제", description = "Headers에 Bearer token 필요, Lion의 id 필요",
            responses = {@ApiResponse(responseCode = "200", description = "Lion 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 Lion 없")})
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteLion(@PathVariable("id") Long id) {
        try {
            lionService.deleteLionById(id);
            return ResponseEntity.ok("Lion 삭제 성공");
        } catch (IllegalLionIdException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}