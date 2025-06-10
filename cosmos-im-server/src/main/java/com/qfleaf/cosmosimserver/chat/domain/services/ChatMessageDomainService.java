package com.qfleaf.cosmosimserver.chat.domain.services;

import com.qfleaf.cosmosimserver.chat.application.commands.SendMessageCommand;
import com.qfleaf.cosmosimserver.chat.domain.aggregates.ChatMessageAggregate;
import com.qfleaf.cosmosimserver.chat.domain.events.ChatMessageSendEvent;
import com.qfleaf.cosmosimserver.chat.domain.repositories.ChatMessageReadRepository;
import com.qfleaf.cosmosimserver.chat.interfaces.rest.response.MessageQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageDomainService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ChatMessageReadRepository chatMessageReadRepository;

    public ChatMessageAggregate createMessage(SendMessageCommand command) {
        ChatMessageAggregate message = ChatMessageAggregate.createMessage(
                command.senderId(),
                command.receiverId(),
                command.content()
        );
        log.debug("create chat message: {}", message);
        applicationEventPublisher.publishEvent(new ChatMessageSendEvent(message));
        return message;
    }

    public List<MessageQueryResponse> getAllMessages(long loginIdAsLong) {
        return chatMessageReadRepository.getAll(loginIdAsLong);
    }
}
