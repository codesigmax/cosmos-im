package com.qfleaf.cosmosimserver.user.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPresenceRepository {
    private static final String ONLINE_KEY = "im:presence:online";
    
    private final RedisTemplate<String, String> redisTemplate;
    
    public void markOnline(String userId, String connectionId) {
        // 使用有序集合存储，score为时间戳
        redisTemplate.opsForZSet().add(
            ONLINE_KEY, 
            userId, 
            System.currentTimeMillis()
        );
        // 单独存储连接信息
        redisTemplate.opsForValue().set(
            "im:connection:" + userId, 
            connectionId
        );
    }
    
    public boolean isOnline(String userId) {
        return redisTemplate.opsForZSet().score(ONLINE_KEY, userId) != null;
    }
}
