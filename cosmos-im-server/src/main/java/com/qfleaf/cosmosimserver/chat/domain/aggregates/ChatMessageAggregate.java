package com.qfleaf.cosmosimserver.chat.domain.aggregates;

import com.qfleaf.cosmosimserver.chat.domain.entites.ChatMessageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageAggregate {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Instant createdAt;

    public static ChatMessageAggregate createMessage(
            Long senderId,
            Long receiverId,
            String content
    ) {
        ChatMessageAggregate chatMessageAggregate = new ChatMessageAggregate();
        chatMessageAggregate.senderId = senderId;
        chatMessageAggregate.receiverId = receiverId;
        chatMessageAggregate.content = content;
        chatMessageAggregate.createdAt = Instant.now();
        return chatMessageAggregate;
    }

    public ChatMessageEntity toEntity() {
        return ChatMessageEntity.builder()
                .id(id)
                .senderId(senderId)
                .receiverId(receiverId)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}
