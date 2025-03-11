package com.example.pharmacy.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "错误代码枚举")
public enum ErrorCode {
    SUCCESS(200,"操作成功"),

    BAD_REQUEST(400,"请求参数错误"),
    UNAUTHORIZED(401,"未授权"),
    FORBIDDEN(403,"权限不足"),
    NOT_FOUND(404,"资源未找到"),
    INTERNAL_SERVER_ERROR(500,"服务器内部错误"),

    USER_ALREADY_EXISTS(1001,"用户已存在"),
    USER_NOT_FOUND(1002,"用户不存在"),
    PASSWORD_ERROR(1003,"密码错误"),
    USER_NOT_FOUND_OR_PASSWORD_ERROR(1004,"用户不存在或密码错误"),
    TOKEN_EXPIRED(1004,"Token已过期"),
    TOKEN_INVALID(1005,"Token无效"),

    MEDICINE_NOT_FOUND(2001, "药品不存在"),
    MEDICINE_ALREADY_EXISTS(2002,"药品已存在")

    ;

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
