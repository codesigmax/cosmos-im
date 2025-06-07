package com.qfleaf.cosmosimserver.user.domain.valueobjects;

import com.qfleaf.cosmosimserver.core.domain.ValueObject;

import java.util.regex.Pattern;

public record Username(String value) implements ValueObject {
    public Username {
        // 校验规则：4-20位字母数字下划线
        if (!Pattern.matches("^\\w{4,20}$", value)) {
            throw new IllegalArgumentException("用户名需由4-20位的字母、数字或下划线组成");
        }
    }
}