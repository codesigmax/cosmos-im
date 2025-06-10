package com.qfleaf.cosmosimserver.chat.interfaces.ws;

import com.qfleaf.cosmosimserver.chat.interfaces.ws.cache.ChatSessionCache;
import com.qfleaf.cosmosimserver.core.SpringContextHolder;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
@ServerEndpoint("/chat/{uid}")
public class ChatEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        log.debug("开启会话 {} ", session.getId());
        Long uid = Long.valueOf(session.getPathParameters().get("uid"));
        ChatSessionCache sessionCache = SpringContextHolder.getBean(ChatSessionCache.class);
        StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        redisTemplate.convertAndSend("user_chat_channel", uid.toString());
        sessionCache.registerSession(uid, session);
    }

    @OnClose
    public void onClose(Session session) {
        Long uid = Long.valueOf(session.getPathParameters().get("uid"));
        ChatSessionCache sessionCache = SpringContextHolder.getBean(ChatSessionCache.class);
        sessionCache.removeSession(uid);
        log.debug("关闭会话 {} ", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("接收到消息 {} ", message);
        session.getAsyncRemote().sendText("已送达");
    }
}
