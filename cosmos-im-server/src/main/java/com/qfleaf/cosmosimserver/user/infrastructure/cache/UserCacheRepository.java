package com.qfleaf.cosmosimserver.user.infrastructure.cache;

import com.qfleaf.cosmosimserver.user.infrastructure.dto.UserDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCacheRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    
    private static final String USER_KEY_PREFIX = "im:user:info:";
    
    public Optional<UserDetailDTO> findById(Long userId) {
        String key = USER_KEY_PREFIX + userId;
        UserDetailDTO detail = (UserDetailDTO) redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(detail);
    }
    
    public void cacheUser(UserDetailDTO detail) {
        String key = USER_KEY_PREFIX + detail.getUser().getId();
        redisTemplate.opsForValue().set(
            key, 
            detail,
            Duration.ofMinutes(30) // TTL设置
        );
    }
}
