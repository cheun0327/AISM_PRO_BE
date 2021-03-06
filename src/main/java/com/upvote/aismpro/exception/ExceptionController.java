package com.upvote.aismpro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    // 이미지 파일 크기 초과 예외
    @ExceptionHandler(MultipartException.class)
    ResponseEntity<?> handleFileException(HttpServletRequest request, Throwable ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleHttpStatusCodeException(
            ApiException e,
            HttpServletRequest request
    ) {
        int statusCode = e.getHttpStatus().value();
        String message = e.getMessage();
        String path = request.getRequestURL().toString();

        return ResponseEntity
                .status(statusCode)
                .body(new ErrorResponse(statusCode, LocalDateTime.now(), path, message));
    }
}
