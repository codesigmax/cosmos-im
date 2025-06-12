package com.qfleaf.cosmosimserver.chat.domain.repositories;

import com.qfleaf.cosmosimserver.chat.domain.aggregates.ChatMessageAggregate;

public interface ChatMessageRepository {
    ChatMessageAggregate save(ChatMessageAggregate chatMessageAggregate);
}
