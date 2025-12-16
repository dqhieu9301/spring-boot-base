package org.example.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommonResponse<T> {
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime timestamp;

    private T data;

    private Integer errorCode;

    private String message;
}
