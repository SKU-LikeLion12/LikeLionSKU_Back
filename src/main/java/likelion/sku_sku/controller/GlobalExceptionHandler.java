package likelion.sku_sku.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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


    // LectureFile
    @ExceptionHandler(InvalidLectureFileException.class)
    public ResponseEntity<String> invalidLectureFile(InvalidLectureFileException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("그 강의자료 없");
    }
}
