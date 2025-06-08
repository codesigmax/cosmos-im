package com.qfleaf.cosmosimserver.user.interfaces.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import com.qfleaf.cosmosimserver.shared.exception.InvalidArgsException;
import com.qfleaf.cosmosimserver.user.domain.exceptions.PasswordMismatchException;
import com.qfleaf.cosmosimserver.user.domain.exceptions.UserNotFoundException;
import com.qfleaf.cosmosimserver.user.domain.exceptions.VerificationCodeMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserModuleExceptionHandler {
    @ExceptionHandler(PasswordMismatchException.class)
    public ApiResponse<Void> handlePasswordMismatchException(PasswordMismatchException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.failure(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<Void> handleUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.failure(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(VerificationCodeMismatchException.class)
    public ApiResponse<Void> handleVerificationCodeMismatchException(VerificationCodeMismatchException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.failure(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public ApiResponse<Void> handleNotLoginException(NotLoginException e) {
        log.error(e.getMessage());
        return ApiResponse.failure(null, "请登陆后使用");
    }

    @ExceptionHandler(InvalidArgsException.class)
    public ApiResponse<Void> handleInvalidArgsException(InvalidArgsException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.failure(e.getErrorCode(), e.getMessage());
    }
}
