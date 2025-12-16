package org.example.common.exception;

import lombok.Getter;
import org.example.common.enums.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException {
    private final Integer errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    public CommonException(ErrorCodeEnum errorCode, Object... args) {
        super(String.format(errorCode.getMessage(), args));
        this.errorCode = errorCode.getCode();
        this.httpStatus = errorCode.getHttpStatus();
        this.message = String.format(errorCode.getMessage(), args);
    }
}
