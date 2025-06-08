package com.qfleaf.cosmosimserver.contact.infrastructure.cache;

import com.qfleaf.cosmosimserver.contact.domain.aggregates.ContactAggregate;
import com.qfleaf.cosmosimserver.shared.ws.WsMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

import static com.qfleaf.cosmosimserver.contact.infrastructure.cache.consts.CacheKey.CONTACT_AGGREGATE_PREFIX_KEY;
import static com.qfleaf.cosmosimserver.contact.infrastructure.cache.consts.CacheKey.NOTIFICATION_PREFIX_KEY;

@Repository
@RequiredArgsConstructor
public class ContactCacheRepository {
    private final RedisTemplate<String, WsMessage<?>> notificationRedisTemplate;
    private final RedisTemplate<String, ContactAggregate> contactAggregateRedisTemplate;

    public void addNotification(Long receiverId, WsMessage<?> jsonPayload) {
        // todo 缓存优化
        String key = NOTIFICATION_PREFIX_KEY + receiverId;
        notificationRedisTemplate.opsForSet().add(key, jsonPayload);
    }

    public Set<WsMessage<?>> getNotifications(Long userId) {
        String key = NOTIFICATION_PREFIX_KEY + userId;
        return notificationRedisTemplate.opsForSet().members(key);
    }

    public void clearNotifications(Long userId) {
        String key = NOTIFICATION_PREFIX_KEY + userId;
        notificationRedisTemplate.delete(key);
    }

    public void setContactAggregate(String eventId, ContactAggregate contactAggregate) {
        String key = CONTACT_AGGREGATE_PREFIX_KEY + eventId;
        contactAggregateRedisTemplate.opsForValue().set(key, contactAggregate);
    }

    public ContactAggregate getContactAggregate(String eventId) {
        String key = CONTACT_AGGREGATE_PREFIX_KEY + eventId;
        return contactAggregateRedisTemplate.opsForValue().get(key);
    }
}
