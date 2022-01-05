package com.upvote.aismpro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    // 파일 얿로도 용량 초과 에러 처리
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    ResponseEntity<?> handleFileException(HttpServletRequest request, Throwable ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
