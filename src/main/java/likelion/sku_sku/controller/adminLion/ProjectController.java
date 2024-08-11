package likelion.sku_sku.controller.adminLion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Project;
import likelion.sku_sku.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static likelion.sku_sku.dto.ProjectDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
//@PreAuthorize("hasRole('ADMIN_LION')")
@Tag(name = "관리자 페이지: Project 관련")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "(민규) Project 추가", description = "Headers에 Bearer token 필요, Project의 title, subTitle, image 필요, body에 form-data로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "생성"),
                    @ApiResponse(responseCode = "409", description = "그 title 이미 있")})
    @PostMapping("/add")
    public ResponseEntity<?> addProject(ProjectCreateRequest request) throws IOException {
            Project project = projectService.addProject(
                    request.getClassTh(),
                    request.getTitle(),
                    request.getSubTitle(),
                    request.getUrl(),
                    request.getImage());
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @Operation(summary = "(민규) Project 수정", description = "Headers에 Bearer token 필요, Project의 id, title, subTitle 필요, image는 안바꾸고 싶으면 안넣으면 됨, , body에 form-data로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공 후 변경된 정보를 포함한 객체 생성"),
                    @ApiResponse(responseCode = "409", description = "그 title 이미 있"),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 Project 없")})
    @PutMapping("/update")
    public ResponseEntity<?> updateProject(ProjectUpdateRequest request) throws IOException {
            Project project = projectService.updateProject(
                    request.getId(),
                    request.getClassTh(),
                    request.getTitle(),
                    request.getSubTitle(),
                    request.getUrl(),
                    request.getImage());
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @Operation(summary = "(민규) id로 Project 개별 정보 조회", description = "Headers에 Bearer token 필요, Project의 ID 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회를 하면 프로젝트 제목, 프로젝트 부제목, 프로젝트 사진이 출력."),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 Project 없")})
    @GetMapping("")
    public ResponseEntity<ResponseProjectUpdate> findProjectById(@RequestParam Long projectId) {
        ResponseProjectUpdate responseProject = projectService.findProjectById(projectId);
            return ResponseEntity.status(HttpStatus.OK).body(responseProject);
    }

    @Operation(summary = "(민규) 모든 Project 정보 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "200", description = "모든 프로젝트 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "Project 하나도 없")})
    @GetMapping("/all")
    public ResponseEntity<List<ResponseIdProjectUpdate>> findProjectAll() {
        List<ResponseIdProjectUpdate> responseIdProjectUpdate = projectService.findProjectAllIdDesc();
        if (responseIdProjectUpdate.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseIdProjectUpdate);
    }

    @Operation(summary = "(민규) Project 삭제", description = "Headers에 Bearer token 필요, Project의 id 필요",
              responses = {@ApiResponse(responseCode = "200", description = "프로젝트 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "그런 id 가진 Project 없")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") Long id) {
            projectService.deleteProject(id);
            return ResponseEntity.ok("Project 삭제 성공");
    }
}
