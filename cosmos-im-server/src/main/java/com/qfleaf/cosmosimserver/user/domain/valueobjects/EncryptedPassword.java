package com.qfleaf.cosmosimserver.user.domain.valueobjects;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Getter
public class EncryptedPassword {
    private final String value;

    public EncryptedPassword(String encryptedValue) {
        // todo fix校验逻辑错误
        if (!StringUtils.hasText(encryptedValue)) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (encryptedValue.length() < 8) {
            throw new IllegalArgumentException("密码长度至少为8位");
        }
        this.value = encryptedValue;
    }

    public boolean matches(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, value);
    }
}
