package com.qfleaf.cosmosimserver.shared.web;

import com.qfleaf.cosmosimserver.shared.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;

    private ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "请求成功", data);
    }

    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(200, message, data);
    }

    public static <T> ApiResponse<T> failure(ErrorCode errorCode, String message) {
        if (StringUtils.hasText(message)) {
            return new ApiResponse<>(500, message, null);
        }
        return new ApiResponse<>(500, errorCode.getDefaultMessage(), null);
    }
}
