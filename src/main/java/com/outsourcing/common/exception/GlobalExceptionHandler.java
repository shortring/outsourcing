package com.outsourcing.common.exception;

import com.outsourcing.common.dto.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.error("MethodArgumentNotValidException 발생 : {} ", ex.getMessage());

        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();

        return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(ex.getStatusCode(), message));
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handlerCustomException(CustomException ex) {
        log.error("CustomException 발생 : {} ", ex.getMessage());

        return ResponseEntity.status(ex.getErrorMessage().getStatus()).body(new ErrorResponse(ex.getErrorMessage()));
    }
}
