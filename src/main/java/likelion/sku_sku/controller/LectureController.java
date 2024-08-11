package likelion.sku_sku.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.sku_sku.domain.Lecture;
import likelion.sku_sku.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static likelion.sku_sku.dto.LectureDTO.*;
import static likelion.sku_sku.dto.LectureDTO.createLectureRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
@Tag(name = "관리자 페이지: Lecture 관련")
public class LectureController {

    private final LectureService lectureService;

    @Operation(summary = "(민규) 강의자료 추가", description = "Headers에 Bearer token 필요, 강의의 trackType, title, 강의자료 필요, body에 form-data로 넣어야 함",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<?> uploadFiles(@RequestHeader("Authorization") String bearer,
                                         createLectureRequest request) throws IOException {
        List<Lecture> lectureFiles = lectureService.createLecture(bearer, request);
        return ResponseEntity.ok(lectureFiles);
    }

    @Operation(summary = "(민규) 강의자료 수정", description = "Headers에 Bearer token 필요, 강의 ID와 수정할 정보를 body에 form-data로 포함시켜야 함",
            responses = {@ApiResponse(responseCode = "200", description = "수정 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 Id의 강의를 찾을 수 없음")})
    @PutMapping("/update")
    public ResponseEntity<?> updateLecture(@RequestHeader("Authorization") String bearer,
                                           updateLectureRequest request) throws IOException {
        Lecture updatedLecture = lectureService.updateLecture(bearer, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedLecture);
    }

    @Operation(summary = "(민규) id로 강의자료 개별 조회", description = "Headers에 Bearer token 필요, Project의 ID 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회를 하면 강의 trackType, 제목, 작성자, 조회수, 작성 시간, 수정 시간, 강의자료 나옴"),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 강의 없")})
    @GetMapping("")
    public ResponseEntity<ResponseLecture> findLectureById(@RequestParam Long lectureId) {
        ResponseLecture responseLecture = lectureService.finaLectureById(lectureId);
        return ResponseEntity.status(HttpStatus.OK).body(responseLecture);
    }

    @Operation(summary = "(민규) 모든 강의자료 조회", description = "Headers에 Bearer token 필요",
            responses = {@ApiResponse(responseCode = "200", description = "모든 강의 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "강의 하나도 없")})
    @GetMapping("/all")
    public ResponseEntity<List<Lecture>> getAllLecture() {
        List<Lecture> lectureFiles = lectureService.findAllLecture();
        if (lectureFiles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(lectureFiles);
    }

    @Operation(summary = "(민규) 강의자료 삭제", description = "Headers에 Bearer token 필요, 삭제할 강의의 ID 필요",
            responses = {@ApiResponse(responseCode = "200", description = "강의자료 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "그런 id 가진 강의 없")})
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLecture(@RequestHeader("Authorization") String bearer,
                                           @RequestParam Long lectureId) {
        lectureService.deleteLectureById(lectureId);
        return ResponseEntity.status(HttpStatus.OK).body("강의자료 삭제 성공");
    }

}
