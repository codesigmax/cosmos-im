package com.qfleaf.cosmosimserver.contact.interfaces.ws;

import com.qfleaf.cosmosimserver.contact.interfaces.ws.cache.ContactNotificationSessionCache;
import com.qfleaf.cosmosimserver.core.SpringContextHolder;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
@ServerEndpoint("/contacts/notification/{uid}")
public class ContactNotificationEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        log.info("会话开启 {}", session);
        Long uid = Long.valueOf(session.getPathParameters().get("uid"));
        ContactNotificationSessionCache sessionCache = SpringContextHolder.getBean(ContactNotificationSessionCache.class);
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        redisTemplate.convertAndSend("user_online_channel", uid.toString());
        sessionCache.registerSession(uid, session);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("会话关闭 {}", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("接到消息 {}", message);
        session.getAsyncRemote().sendText(message);
    }
}
