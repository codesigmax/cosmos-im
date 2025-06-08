package com.qfleaf.cosmosimserver.shared.exception.handler;

import com.qfleaf.cosmosimserver.shared.exception.InvalidArgsException;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidArgsException.class)
    public ApiResponse<Void> handleInvalidArgsException(InvalidArgsException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.failure(e.getErrorCode(), e.getMessage());
    }

}
