package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Project;
import likelion.sku_sku.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static likelion.sku_sku.dto.ProjectDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@PreAuthorize("hasRole('ADMIN_LION')")
@Tag(name = "관리자 페이지: 프로젝트 관련")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "(민규) 프로젝트 추가", description = "Header에 Bearer 토큰 필요, 프로젝트 제목, 프로젝트 부제목, 프로젝트 사진 필요",
            responses = {@ApiResponse(responseCode = "201", description = "생성"),
                    @ApiResponse(responseCode = "409", description = "title 이미 있지롱")})
    @PostMapping("/add")
    public ResponseEntity<?> addProject(ProjectCreateRequest request) throws IOException {
        try {
            Project project = projectService.addProject(request.getClassTh(), request.getTitle(), request.getSubTitle(), request.getImage());
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @Operation(summary = "(민규) 프로젝트 수정", description = "Header에 Bearer 토큰 필요, 프로젝트 id, 프로젝트 제목, 프로젝트 부제목 필요, 사진은 안바꾸고 싶으면 안넣으면 됨",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공 후 변경된 정보를 포함한 객체 생성"),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 project 없지롱")})
    @PutMapping("/update")
    public ResponseEntity<?> updateProject(ProjectUpdateRequest request) throws IOException {
        try {
            Project project = projectService.updateProject(request.getId(), request.getClassTh(), request.getTitle(), request.getSubTitle(), request.getImage());
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "(민규) 프로젝트 조회", description = "Header에 Bearer 토큰 필요, 프로젝트 대한 정보 조회",
            responses = {@ApiResponse(responseCode = "200", description = "조회를 하면 프로젝트 제목, 프로젝트 부제목, 프로젝트 사진이 출력."),
                    @ApiResponse(responseCode = "404", description = "그런 project 없는디요")})
    @GetMapping("")
    public ResponseEntity<ResponseProjectUpdate> findProject(TitleRequest request) {
        ResponseProjectUpdate responseProject = projectService.findProject(request.getTitle());
        if (responseProject != null) {
            return ResponseEntity.status(HttpStatus.OK).body(responseProject);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "(민규) 모든 프로젝트 조회", description = "Header에 Bearer 토큰 필요, 모든 프로젝트 대한 정보 조회",
            responses = {@ApiResponse(responseCode = "200", description = "모든 프로젝트 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "그런 project 없는디요")})
    @GetMapping("/all")
    public ResponseEntity<List<Project>> findProjectAll() {
        List<Project> projects = projectService.findProjectAll();
        if (projects.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @Operation(summary = "(민규) 프로젝트 삭제", description = "Header에 Bearer 토큰 필요, 프로젝트 이름 넣으면 해당 프로젝트 삭제",
            responses = {@ApiResponse(responseCode = "200", description = "프로젝트 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "그런 title 가진 project 없지롱")})
    @DeleteMapping("")
    public ResponseEntity<String> deleteProject(@RequestBody TitleRequest request) {
        try {
            projectService.deleteProject(request.getTitle());
            return ResponseEntity.ok("Project 삭제 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
