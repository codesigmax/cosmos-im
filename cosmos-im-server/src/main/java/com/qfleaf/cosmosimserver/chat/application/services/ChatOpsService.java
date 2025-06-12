package com.qfleaf.cosmosimserver.chat.application.services;

import com.qfleaf.cosmosimserver.chat.application.commands.SendMessageCommand;
import com.qfleaf.cosmosimserver.chat.domain.aggregates.ChatMessageAggregate;
import com.qfleaf.cosmosimserver.chat.domain.services.ChatMessageDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatOpsService {
    private final ChatMessageDomainService domainService;

    public void sendMessage(SendMessageCommand sendMessageCommand) {
        ChatMessageAggregate message = domainService.createMessage(sendMessageCommand);
    }
}
