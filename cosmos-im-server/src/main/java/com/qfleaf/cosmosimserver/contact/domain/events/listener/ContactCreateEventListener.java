package com.qfleaf.cosmosimserver.contact.domain.events.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfleaf.cosmosimserver.contact.domain.events.ContactCreateEvent;
import com.qfleaf.cosmosimserver.contact.infrastructure.cache.ContactCacheRepository;
import com.qfleaf.cosmosimserver.contact.infrastructure.dto.AddContactEventDTO;
import com.qfleaf.cosmosimserver.contact.interfaces.ws.cache.ContactNotificationSessionCache;
import com.qfleaf.cosmosimserver.shared.ws.WsMessage;
import jakarta.annotation.Nonnull;
import jakarta.websocket.Session;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class ContactCreateEventListener
        implements ApplicationListener<ContactCreateEvent>, MessageListener {
    private final ContactNotificationSessionCache sessionCache;
    private final ContactCacheRepository cacheRepo;
    private final ObjectMapper objectMapper;

    @Override
    public void onApplicationEvent(@Nonnull ContactCreateEvent event) {
        log.info("监听到事件 {}", event);
        // 缓存contact聚合对象
        cacheRepo.setContactAggregate(event.getEventId(), event.getContact());
        WsMessage<AddContactEventDTO> payload = WsMessage.create(
                "contact_request",
                AddContactEventDTO.builder()
                        .eventId(event.getEventId())
                        .senderId(event.getContact().getFrom().getOwnerId())
                        .timestamp(event.getTimestamp())
                        .build(),
                event.getTimestamp()
        );
        Long receiverId = event.getContact().getFrom().getContactId();
        Session session = sessionCache.getSession(receiverId);
        if (session != null && session.isOpen()) {
            try {
                session.getAsyncRemote().sendText(objectMapper.writeValueAsString(payload));
            } catch (JsonProcessingException e) {
                log.error("JSON转换异常 {}", e.getMessage(), e);
            }
        } else {
            cacheRepo.addNotification(receiverId, payload);
        }
    }

    @Override
    public void onMessage(@Nonnull Message message, byte[] pattern) {
        log.info("收到消息 {}", message);
        Long userId = Long.valueOf(new String(message.getBody()));
        Session session = sessionCache.getSession(userId);
        if (session != null && session.isOpen()) {
            Set<WsMessage<?>> messages = cacheRepo.getNotifications(userId);
            if (messages != null) {
                log.debug(Arrays.toString(messages.toArray()));
                for (WsMessage<?> msg : messages) {
                    try {
                        session.getBasicRemote().sendText(objectMapper.writeValueAsString(msg));
                    } catch (IOException e) {
                        log.error("IO异常 {}", e.getMessage(), e);
                    }
                }
                // 清空该用户的离线消息
                cacheRepo.clearNotifications(userId);
            }
        }
    }
}
