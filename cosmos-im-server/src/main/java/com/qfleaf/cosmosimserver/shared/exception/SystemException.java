package com.qfleaf.cosmosimserver.shared.exception;

import lombok.Getter;

import java.util.concurrent.TimeoutException;

@Getter
public class SystemException extends BaseException {
    private final String componentType; // 组件类型: DB/Cache/MQ等
    private final String operation;    // 失败操作: query/save/publish
    private int maxRetries = 3;        // 默认最大重试次数

    public SystemException(
            ErrorCode errorCode,
            String componentType,
            String operation,
            Throwable cause
    ) {
        super(errorCode,
                String.format("[%s]操作失败: %s", componentType, operation),
                cause);
        this.componentType = componentType;
        this.operation = operation;
    }

    public SystemException withRetryPolicy(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    // 是否可重试（根据错误类型判断）
    public boolean isRecoverable() {
        return getCause() instanceof TimeoutException
                || getErrorCode() == ErrorCode.DB_DEADLOCK;
    }

    // 生成技术诊断报告
    public String generateTechReport() {
        return String.format("""
                        === 技术故障报告 ===
                        组件类型: %s
                        操作: %s
                        错误码: %s
                        根本原因: %s
                        """,
                componentType,
                operation,
                getErrorCode().getCode(),
                getRootCauseMessage());
    }

    private Object getRootCauseMessage() {
        return getCause().getMessage();
    }
}
