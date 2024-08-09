package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.dto.LectureDTO;
import likelion.sku_sku.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static likelion.sku_sku.dto.LectureDTO.*;
import static likelion.sku_sku.dto.LectureDTO.uploadLectureRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
@Tag(name = "관리자 페이지: Lecture 관련")
public class LectureController {

    private final LectureService lectureService;

    @Operation(summary = "(민규) 강의자료 추가", description = "Headers에 Bearer token 필요, 강의자료의 title, subTitle, image 필요, body에 form-data로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<?> uploadFiles(@RequestHeader("Authorization") String bearer,
                                         @ModelAttribute uploadLectureRequest request) throws IOException {
        List<Lecture> lectureFiles = lectureService.uploadLectureFiles(bearer, request);
        return ResponseEntity.ok(lectureFiles);
    }

    @Operation(summary = "(민규) id로 강의자료 개별 조회", description = "Headers에 Bearer token 필요, Project의 ID 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회를 하면 프로젝트 제목, 프로젝트 부제목, 프로젝트 사진이 출력."),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 Project 없")})
    @GetMapping("/{lectureId}")
    public ResponseEntity<ResponseLecture> findLectureById(@PathVariable("lectureId") Long lectureId) {
        ResponseLecture responseLecture = lectureService.finaLectureById(lectureId);
        return ResponseEntity.status(HttpStatus.OK).body(responseLecture);
    }

    @Operation(summary = "(민규) 모든 강의자료 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "200", description = "모든 Lion 조회 성공")})
    @GetMapping("/all")
    public ResponseEntity<List<Lecture>> getAllLecture() {
        List<Lecture> lectureFiles = lectureService.findAllLecture();
        return ResponseEntity.ok(lectureFiles);
    }
}
