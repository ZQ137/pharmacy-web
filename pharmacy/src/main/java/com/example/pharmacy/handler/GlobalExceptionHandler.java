package com.example.pharmacy.handler;

import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.common.Result;
import com.example.pharmacy.exception.CustomException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Result<?> handleCustomException(CustomException e){
        return Result.error(e.getErrorCode());
    }

    /**
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(ErrorCode.BAD_REQUEST);
    }

    /**
     * 处理请求参数格式错误
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e){
        return Result.error(ErrorCode.BAD_REQUEST);
    }

    /**
     * 处理Spring Security 权限异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleAccessDeniedException(AccessDeniedException e){
        return Result.error(ErrorCode.FORBIDDEN);
    }

    /**
     * 处理所有未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        return Result.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
