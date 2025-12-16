package org.example.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodeEnum {
    SUCCESS(0, "Success", HttpStatus.OK),

    INVALID_REQUEST(1001, "Invalid request data", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1002, "Unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(1003, "Forbidden", HttpStatus.FORBIDDEN),
    NOT_FOUND(1004, "%s not found", HttpStatus.NOT_FOUND),

    SYSTEM_ERROR(9999, "Internal server error",
            HttpStatus.INTERNAL_SERVER_ERROR);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCodeEnum(Integer code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
