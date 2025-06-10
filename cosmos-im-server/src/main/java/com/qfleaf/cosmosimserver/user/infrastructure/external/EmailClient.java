package com.qfleaf.cosmosimserver.user.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailClient {
    private final JavaMailSender mailSender;

    private final RedisTemplate<String, String> redisTemplate;

    private static final String REDIS_KEY_EMAIL = "im:email:code";

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmailCode(String email, String verificationCode) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("验证码");
        simpleMailMessage.setText(verificationCode);
        mailSender.send(simpleMailMessage);
    }

    public boolean verifyCode(String email, String code) {
        String realCode = (String) redisTemplate.opsForHash().get(REDIS_KEY_EMAIL, email);
        if (realCode == null) {
            return false;
        }
        return realCode.equals(code);
    }

    @Async
    public void sendWelcomeEmail(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("欢迎使用cosmos-im");
        simpleMailMessage.setText("🤗");
        mailSender.send(simpleMailMessage);
    }
}
