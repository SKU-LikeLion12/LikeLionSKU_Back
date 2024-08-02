package likelion.sku_sku.controller;

import likelion.sku_sku.exception.InvalidEmailException;
import likelion.sku_sku.exception.InvalidIdException;
import likelion.sku_sku.exception.InvalidTitleException;
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

    // Project
    @ExceptionHandler(InvalidTitleException.class)
    public ResponseEntity<String> invalidTitle(InvalidTitleException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("그 title 이미 있");
    }

    // Article


    // Assignment


    // LectureFile


}
