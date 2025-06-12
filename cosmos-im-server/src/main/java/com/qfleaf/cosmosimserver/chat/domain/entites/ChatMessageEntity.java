package com.qfleaf.cosmosimserver.chat.domain.entites;

import com.qfleaf.cosmosimserver.chat.domain.aggregates.ChatMessageAggregate;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageEntity {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Instant createdAt;

    public ChatMessageAggregate toAggerates() {
        return ChatMessageAggregate.builder()
                .id(id)
                .senderId(senderId)
                .receiverId(receiverId)
                .content(content)
                .createdAt(createdAt)
                .build();
    }
}
