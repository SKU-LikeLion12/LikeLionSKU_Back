package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Lion;
import likelion.sku_sku.service.LionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static likelion.sku_sku.dto.LionDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/lion")
@PreAuthorize("hasRole('ADMIN_LION')")
@Tag(name = "관리자 페이지: lion 관련")
public class LionController {
    private final LionService lionService;

    @Operation(summary = "(민규) lion 추가", description = "lion 이름, lion 이메일, lion 권한 필요",
            responses = {@ApiResponse(responseCode = "201", description = "생성"),
                    @ApiResponse(responseCode = "", description = "")})
    @PostMapping("/add")
    public ResponseEntity<Lion> addLion(@RequestBody LionCreateRequest request) {
        Lion lion = lionService.addLion(request.getName(), request.getEmail(), request.getRoleType());
        return ResponseEntity.status(HttpStatus.CREATED).body(lion);
    }

    @Operation(summary = "(민규) lion 수정", description = "lion id, lion 이름, lion 이메일, lion 권한 필요",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공 후 변경된 정보를 포함한 객체 생성 "),
                    @ApiResponse(responseCode = "", description = "")})
    @PutMapping("/update")
    public ResponseEntity<ResponseLionUpdate> updateLion(@RequestBody LionUpdateRequest request) {
        Lion lion = lionService.updateLion(request.getId(), request.getName(), request.getEmail(), request.getRoleType());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseLionUpdate(lion.getName(), lion.getEmail(), lion.getRole()));
    }

    @Operation(summary = "(민규) lion 권한 변경", description = "id, role 필요",
            responses = {@ApiResponse(responseCode = "200", description = "권한 변경 성공"),
                    @ApiResponse(responseCode = "404", description = "lion이 존재하지 않음")})
    @PutMapping("/role")
    public ResponseEntity<Void> updateLionRole(IdRoleRequest request) {
        Lion lion = lionService.updateLionRole(request.getId(), request.getRoleType());
        return lion != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


    @Operation(summary = "(민규) lion 이름으로 조회", description = "lion 이름 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회를 하면 lion 이름, lion 이메일, lion 권한이 출력."),
                    @ApiResponse(responseCode = "404", description = "")})
    @GetMapping("/name")
    public ResponseEntity<List<Lion>> getLionsByName(NameRequest request) {
        List<Lion> lions = lionService.getLionsByName(request.getName());
        if (lions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(lions);
    }

    @Operation(summary = "(민규) lion 이메일로 조회", description = "lion email 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회를 하면 lion 이름, lion 이메일, lion 권한이 출력."),
                    @ApiResponse(responseCode = "404", description = "lion이 존재하지 않음")})
    @GetMapping("/email")
    public ResponseEntity<Lion> getLionByEmail(EmailRequest request) {
        Optional<Lion> lion = lionService.getLionByEmail(request.getEmail());
        return lion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "(민규) 모든 lion 조회", description = "모든 lion에 대한 정보 조회",
            responses = {@ApiResponse(responseCode = "200", description = "모든 lion 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "lion이 존재하지 않음")})
    @GetMapping("/all")
    public ResponseEntity<List<Lion>> getAllLions() {
        List<Lion> lions = lionService.getAllLions();
        if (lions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(lions);
    }

    @Operation(summary = "(민규) lion 삭제", description = "lion email 필요",
            responses = {@ApiResponse(responseCode = "200", description = "lion 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "")})
    @DeleteMapping("")
    public ResponseEntity<Void> deleteLion(EmailRequest request) {
        lionService.deleteLion(request.getEmail());
        return ResponseEntity.noContent().build();
    }
}
