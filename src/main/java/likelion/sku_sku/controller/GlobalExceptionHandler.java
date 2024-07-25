package likelion.sku_sku.controller;

import likelion.sku_sku.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Lion
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> invalidEmail(InvalidEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("그 email 이미 있");
    }

    @ExceptionHandler(InvalidLionIdException.class)
    public ResponseEntity<String> invalidLionId(InvalidLionIdException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("그런 id 가진 Lion 없");
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<String> invalidRole(InvalidRoleException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 role 값");
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGlobalException(Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다: " + e.getMessage());
//    }

    // Project
    @ExceptionHandler(InvalidProjectIdException.class)
    public ResponseEntity<String> invalidProjectId(InvalidProjectIdException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("그런 id 가진 project 없");
    }

    @ExceptionHandler(InvalidTitleException.class)
    public ResponseEntity<String> invalidTitle(InvalidTitleException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("그 title 이미 있");
    }
}
