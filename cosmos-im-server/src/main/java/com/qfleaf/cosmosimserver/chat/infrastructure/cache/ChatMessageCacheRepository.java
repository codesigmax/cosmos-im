package com.qfleaf.cosmosimserver.chat.infrastructure.cache;

import com.qfleaf.cosmosimserver.chat.domain.aggregates.ChatMessageAggregate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageCacheRepository {
    private final RedisTemplate<String, ChatMessageAggregate> redisTemplate;

    private static final String REDIS_CHAT_MESSAGE_CACHE = "chat:offline:message:";

    public void setOfflineMessage(ChatMessageAggregate chatMessageAggregate) {
        redisTemplate.opsForList().rightPush(REDIS_CHAT_MESSAGE_CACHE + chatMessageAggregate.getReceiverId(), chatMessageAggregate);
    }

    public List<ChatMessageAggregate> getOfflineMessageCache(Long userId) {
        return redisTemplate.opsForList().range(REDIS_CHAT_MESSAGE_CACHE + userId, 0, -1);
    }

    public void clear(Long userId) {
        redisTemplate.delete(REDIS_CHAT_MESSAGE_CACHE + userId);
    }
}
