package com.qfleaf.cosmosimserver.user.domain.valueobjects;

import java.util.regex.Pattern;

public record Email(String value) {
    public Email {
        if (!Pattern.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^@][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", value)) {
            throw new IllegalArgumentException("邮箱格式错误或不被允许");
        }
    }
}
