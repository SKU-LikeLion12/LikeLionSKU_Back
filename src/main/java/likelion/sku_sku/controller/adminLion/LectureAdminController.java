package likelion.sku_sku.controller.adminLion;

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

import static likelion.sku_sku.dto.LectureDTO.createLectureRequest;
import static likelion.sku_sku.dto.LectureDTO.updateLectureRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/lecture")
@Tag(name = "관리자 기능: 강의자료 관련")
public class LectureAdminController {
    private final LectureService lectureService;

    @Operation(summary = "(민규) 강의자료 추가", description = "Headers에 Bearer token 필요, body에 form-data로 강의자료의 trackType, title, files 필요",
            responses = {@ApiResponse(responseCode = "201", description = "생성")})
    @PostMapping("/add")
    public ResponseEntity<Lecture> uploadFiles(@RequestHeader("Authorization") String bearer,
                                         createLectureRequest request) throws IOException {
        Lecture lectureFiles = lectureService.createLecture(bearer, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(lectureFiles);
    }

    @Operation(summary = "(민규) 강의자료 수정", description = "Headers에 Bearer token 필요, body에 form-data로 강의자료 id와 수정하고 싶은 값만 넣으면 됨",
            responses = {@ApiResponse(responseCode = "201", description = "수정 성공"),
                    @ApiResponse(responseCode = "404", description = "그 id에 해당하는 값 없")})
    @PutMapping("/update")
    public ResponseEntity<Lecture> updateLecture(@RequestHeader("Authorization") String bearer,
                                           updateLectureRequest request) throws IOException {
        Lecture updatedLecture = lectureService.updateLecture(bearer, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedLecture);
    }

    @Operation(summary = "(민규) 강의자료 삭제", description = "Headers에 Bearer token 필요, 쿼리 파라미터로 삭제할 강의자료의 id 필요",
            responses = {@ApiResponse(responseCode = "200", description = "강의자료 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "그 id에 해당하는 값 없")})
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLecture(@RequestParam Long lectureId) {
        lectureService.deleteLecture(lectureId);
        return ResponseEntity.status(HttpStatus.OK).body("강의자료 삭제 성공");
    }

}
