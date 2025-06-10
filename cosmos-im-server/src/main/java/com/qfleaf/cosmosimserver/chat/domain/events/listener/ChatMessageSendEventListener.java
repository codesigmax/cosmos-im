package com.qfleaf.cosmosimserver.chat.domain.events.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfleaf.cosmosimserver.chat.domain.aggregates.ChatMessageAggregate;
import com.qfleaf.cosmosimserver.chat.domain.events.ChatMessageSendEvent;
import com.qfleaf.cosmosimserver.chat.infrastructure.cache.ChatMessageCacheRepository;
import com.qfleaf.cosmosimserver.chat.infrastructure.persistence.ChatMessageRepositoryImpl;
import com.qfleaf.cosmosimserver.chat.interfaces.ws.cache.ChatSessionCache;
import com.qfleaf.cosmosimserver.shared.exception.ErrorCode;
import com.qfleaf.cosmosimserver.shared.exception.SystemException;
import com.qfleaf.cosmosimserver.shared.ws.WsMessage;
import jakarta.annotation.Nonnull;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageSendEventListener
        implements ApplicationListener<ChatMessageSendEvent>, MessageListener {

    private final ChatSessionCache sessionCache;
    private final ObjectMapper objectMapper;
    private final ChatMessageRepositoryImpl chatMessageRepo;
    private final ChatMessageCacheRepository chatMessageCacheRepo;

    @Override
    public void onApplicationEvent(ChatMessageSendEvent event) {
        ChatMessageAggregate chatMessage = event.getChatMessage();
        ChatMessageAggregate savedMsg = chatMessageRepo.save(chatMessage);
        Session receiverSession = sessionCache.getSession(savedMsg.getReceiverId());

        if (receiverSession != null && receiverSession.isOpen()) {
            WsMessage<ChatMessageAggregate> message =
                    WsMessage.create("chat_message", savedMsg, savedMsg.getCreatedAt().getEpochSecond());
            try {
                receiverSession.getAsyncRemote().sendText(objectMapper.writeValueAsString(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // 缓存离线消息
        chatMessageCacheRepo.setOfflineMessage(savedMsg);
        // 发送消息回执
        Session senderSession = sessionCache.getSession(savedMsg.getSenderId());
        if (senderSession != null && senderSession.isOpen()) {
            senderSession.getAsyncRemote().sendText("已送达");
        }
    }

    @Override
    public void onMessage(@Nonnull Message message, byte[] pattern) {
        log.info("收到消息 {}", message);
        Long userId = Long.valueOf(new String(message.getBody()));
        Session session = sessionCache.getSession(userId);
        if (session != null && session.isOpen()) {
            List<ChatMessageAggregate> messages = chatMessageCacheRepo.getOfflineMessageCache(userId);
            if (messages != null) {
                log.debug(Arrays.toString(messages.toArray()));
                for (ChatMessageAggregate msg : messages) {
                    try {
                        WsMessage<ChatMessageAggregate> offlineMsg =
                                WsMessage.create("chat_message", msg, msg.getCreatedAt().getEpochSecond());
                        session.getBasicRemote().sendText(objectMapper.writeValueAsString(offlineMsg));
                    } catch (JsonProcessingException e) {
                        log.error("JsonProcessing异常 {}", e.getMessage());
                        throw new SystemException(
                                ErrorCode.INTERNAL_ERROR,
                                "Jackson",
                                "处理JSON数据失败: " + e.getMessage(),
                                e
                        );
                    } catch (IOException e) {
                        log.error("IO异常 {}", e.getMessage());
                        throw new SystemException(
                                ErrorCode.MESSAGE_REJECTED,
                                "WebSocket",
                                "消息发送失败: " + e.getMessage(),
                                e
                        );
                    }
                }
                // 清空该用户的离线消息
                chatMessageCacheRepo.clear(userId);
            }
        }
    }
}
