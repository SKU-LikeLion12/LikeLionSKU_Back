//package likelion.sku_sku.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import likelion.sku_sku.domain.JoinLectureFiles;
//import likelion.sku_sku.domain.LectureFile;
//import likelion.sku_sku.service.LectureFileService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import springfox.documentation.annotations.ApiIgnore;
//
//import java.io.IOException;
//import java.util.List;
//
//import static likelion.sku_sku.dto.LectureFileDTO.uploadLectureFileRequest;
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/lectureFile")
//@Tag(name = "관리자 페이지: LectureFile 관련")
//public class LectureFileController {
//
//    private final LectureFileService lectureFileService;
//
//    @Operation(summary = "(민규) 강의자료 추가", description = "Headers에 Bearer token 필요, 강의자료의 title, subTitle, image 필요, body에 form-data로 넣어야 함",
//            responses = {@ApiResponse(responseCode = "201", description = "생성")})
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadFiles(@RequestHeader("Authorization") String bearer,
//                                         @ModelAttribute uploadLectureFileRequest request) throws IOException {
//        List<MultipartFile> files = request.getFiles();
//        List<LectureFile> savedLectureFiles = lectureFileService.saveFiles(bearer, request.getTitle(), files);
//        return ResponseEntity.ok(savedLectureFiles);
//    }
//
//    @Operation(summary = "(민규) id로 강의자료 개별 조회", description = "Headers에 Bearer token 필요, 강의자료의 id 필요",
//            responses = {@ApiResponse(responseCode = "200", description = "조회를 하면 강의자료의 이름, 이메일, 역할이 출력."),
//                    @ApiResponse(responseCode = "404", description = "그런 id 가진 강의자료 없")})
//    @GetMapping("/{id}")
//    public ResponseEntity<byte[]> getLectureFileById(@PathVariable Long id) {
//        LectureFile lectureFile = lectureFileService.findLectureFileById(id);
//        JoinLectureFiles joinLectureFile = lectureFile.getJoinLectureFiles().get(0); // 여기서는 첫 번째 파일만 반환한다고 가정
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + joinLectureFile.getFileName() + "\"");
//        headers.add(HttpHeaders.CONTENT_TYPE, joinLectureFile.getFileType());
//
//        return new ResponseEntity<>(joinLectureFile.getDecodedFile(), headers, HttpStatus.OK); // 디코딩된 파일을 반환
//    }
//
//    @Operation(summary = "(민규) 모든 강의자료 조회", description = "Headers에 Bearer token 필요",
//            responses = {@ApiResponse(responseCode = "200", description = "모든 Lion 조회 성공")})
//    @GetMapping("/all")
//    public ResponseEntity<List<LectureFile>> getAllLectureFiles() {
//        List<LectureFile> lectureFiles = lectureFileService.findAllLectureFiles();
//        if (lectureFiles.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(lectureFiles);
//    }
//}
