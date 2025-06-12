package com.qfleaf.cosmosimserver.core.domain.id;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashIdGenerator {
    public static String hashFromLong(long input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(Long.toString(input).getBytes());
            // 编码为 Base64 字符串（可选：substring 截取部分位数）
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes).substring(0, 12); 
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(hashFromLong(123456789L));
    }
}
