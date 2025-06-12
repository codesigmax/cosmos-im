package com.qfleaf.cosmosimserver.chat.domain.events;

import com.qfleaf.cosmosimserver.chat.domain.aggregates.ChatMessageAggregate;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ChatMessageSendEvent extends ApplicationEvent {
    private final ChatMessageAggregate chatMessage;

    public ChatMessageSendEvent(ChatMessageAggregate chatMessage) {
        super(chatMessage);
        this.chatMessage = chatMessage;
    }
}
