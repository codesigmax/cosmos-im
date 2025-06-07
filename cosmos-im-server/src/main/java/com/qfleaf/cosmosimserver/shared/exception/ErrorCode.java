package com.qfleaf.cosmosimserver.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 通用错误域
    INTERNAL_ERROR("SYS_5001", "系统内部错误"),

    // 用户域
    INVALID_ARGS_ERROR("USER_4000", "参数错误"),
    USER_NOT_FOUND("USER_4001", "用户不存在"),
    INVALID_CREDENTIAL("USER_4002", "凭证无效"),

    // IM消息域
    MESSAGE_REJECTED("IM_3001", "消息被拒"),

    // 数据错误域
    DB_DEADLOCK("DB_2631", "数据库死锁");

    // 系统域
    private final String code;
    private final String defaultMessage;

    // 获取所属领域（根据前缀自动解析）
    public String getScope() {
        return code.split("_")[0];
    }
}
