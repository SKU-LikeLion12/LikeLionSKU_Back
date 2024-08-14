package likelion.sku_sku.controller;

import likelion.sku_sku.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // All
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<String> invalidId(InvalidIdException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("그 id에 해당하는 값 없");
    }

    // Lion
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> invalidEmail(InvalidEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("그 email 이미 있");
    }
    @ExceptionHandler(InvalidLionException.class)
    public ResponseEntity<String> invalidLion(InvalidLionException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이런 Lion 없");
    }
    // Project
    @ExceptionHandler(InvalidTitleException.class)
    public ResponseEntity<String> invalidTitle(InvalidTitleException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("그 title 이미 있");
    }

    // Article


    // Assignment
    @ExceptionHandler(InvalidListIdException.class)
    public ResponseEntity<String> invalidListId(InvalidListIdException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 과제 안내물 id 목록이 비어 있");
    }

    // SubmitAssignment
    @ExceptionHandler(InvalidSubmitAssignmentException.class)
    public ResponseEntity<String> InvalidSubmitAssignment(InvalidSubmitAssignmentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("그 제출한 과제 없");
    }

    // LectureFile
    @ExceptionHandler(InvalidLectureFileException.class)
    public ResponseEntity<String> invalidLectureFile(InvalidLectureFileException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("그 강의자료 없");
    }
}
