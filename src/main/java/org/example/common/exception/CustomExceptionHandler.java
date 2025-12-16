package org.example.common.exception;

import lombok.AllArgsConstructor;
import org.example.common.enums.ErrorCodeEnum;
import org.example.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@AllArgsConstructor
public class CustomExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonResponse<Void>> handleCommonException(CommonException ex) {

        CommonResponse<Void> response = new CommonResponse<>();
        response.setTimestamp(LocalDateTime.now());
        response.setErrorCode(ex.getErrorCode());
        response.setMessage(ex.getMessage());
        response.setData(null);

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex) {

        FieldError fieldError = ex.getBindingResult().getFieldError();

        String message = fieldError != null
                ? String.format("Field '%s': %s",
                fieldError.getField(),
                fieldError.getDefaultMessage())
                : "Request Invalid";

        CommonResponse<Void> response = new CommonResponse<>();
        response.setTimestamp(LocalDateTime.now());
        response.setErrorCode(ErrorCodeEnum.INVALID_REQUEST.getCode());
        response.setMessage(message);
        response.setData(null);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
