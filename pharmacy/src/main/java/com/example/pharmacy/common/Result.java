package com.example.pharmacy.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "通用返回结果")
public class Result<T> {

    @Schema(description = "状态码",example = "200")
    private Integer code;

    @Schema(description = "消息",example = "操作成功")
    private String message;

    @Schema(description = "数据")
    private T data;

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(String message) {
        return new Result<T>(200, message, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "success", data);
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
