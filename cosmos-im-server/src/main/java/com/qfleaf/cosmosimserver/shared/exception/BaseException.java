package com.qfleaf.cosmosimserver.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Instant timestamp;
    private final Map<String, Object> contextData;

    protected BaseException(ErrorCode errorCode, String message) {
        this(errorCode, message, true, true); // 默认启用堆栈
    }

    protected BaseException(
            ErrorCode errorCode,
            String message,
            boolean enableSuppression,  // 是否抑制异常链
            boolean writableStackTrace  // 是否生成堆栈轨迹
    ) {
        super(message, null, enableSuppression, writableStackTrace);
        this.errorCode = Objects.requireNonNull(errorCode);
        this.timestamp = Instant.now();
        this.contextData = new ConcurrentHashMap<>();
    }

    public BaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = Objects.requireNonNull(errorCode);
        this.timestamp = Instant.now();
        this.contextData = new ConcurrentHashMap<>();
    }

    // 添加诊断上下文（线程安全）
    public BaseException withContext(String key, Object value) {
        this.contextData.put(key, value);
        return this;
    }

    // 获取异常指纹（用于日志聚合）
    public String getFingerprint() {
        return String.format("%s:%s",
                errorCode.getScope(), // 所属领域
                errorCode.getCode()); // 错误编码
    }

    // Getters（业务关键字段设为final）
    public final ErrorCode getErrorCode() {
        return errorCode;
    }

    public final Instant getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getContextData() {
        return Collections.unmodifiableMap(contextData);
    }
}
