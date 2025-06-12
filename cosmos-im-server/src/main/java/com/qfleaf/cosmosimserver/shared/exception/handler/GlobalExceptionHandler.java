package com.qfleaf.cosmosimserver.shared.exception.handler;

import com.qfleaf.cosmosimserver.shared.exception.InvalidArgsException;
import com.qfleaf.cosmosimserver.shared.exception.SystemException;
import com.qfleaf.cosmosimserver.shared.web.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final HttpServletResponse httpServletResponse;

    @ExceptionHandler(SystemException.class)
    public ApiResponse<Void> handleSystemException(SystemException e) throws IOException {
//        log.error(e.getFingerprint(), e.getMessage(), e);
        log.error("\n系统异常:\n {}", e.generateTechReport());
        httpServletResponse.setStatus(500);
        return ApiResponse.failure(e.getErrorCode(), "服务器内部错误");
    }

    @ExceptionHandler(InvalidArgsException.class)
    public ApiResponse<Void> handleInvalidArgsException(InvalidArgsException e) {
        log.error(e.getMessage(), e);
        return ApiResponse.failure(e.getErrorCode(), e.getMessage());
    }

}
