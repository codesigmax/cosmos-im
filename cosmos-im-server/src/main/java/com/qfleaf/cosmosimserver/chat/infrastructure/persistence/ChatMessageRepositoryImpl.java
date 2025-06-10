package com.qfleaf.cosmosimserver.chat.infrastructure.persistence;

import com.qfleaf.cosmosimserver.chat.domain.aggregates.ChatMessageAggregate;
import com.qfleaf.cosmosimserver.chat.domain.entites.ChatMessageEntity;
import com.qfleaf.cosmosimserver.chat.domain.repositories.ChatMessageRepository;
import com.qfleaf.cosmosimserver.chat.domain.repositories.ChatMessageWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {
    private final ChatMessageWriteRepository chatMessageWriteRepository;

    @Override
    public ChatMessageAggregate save(ChatMessageAggregate chatMessageAggregate) {
        ChatMessageEntity entity = chatMessageAggregate.toEntity();
        chatMessageWriteRepository.insert(entity);
        return entity.toAggerates();
    }
}
