package com.qfleaf.cosmosimserver.user.domain.valueobjects;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class EncryptedPassword {
    private final String value;

    public EncryptedPassword(String encryptedValue) {
        if (!StringUtils.hasText(encryptedValue)) {
            throw new IllegalArgumentException("密码不能为空");
        }
        this.value = encryptedValue;
    }
}
