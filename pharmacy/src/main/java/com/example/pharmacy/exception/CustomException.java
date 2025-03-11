package com.example.pharmacy.exception;

import com.example.pharmacy.common.ErrorCode;
import lombok.Getter;

/**
 * 处理自定义异常
 */
@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }
}
