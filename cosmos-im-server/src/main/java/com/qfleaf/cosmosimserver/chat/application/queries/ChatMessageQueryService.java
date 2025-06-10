package com.qfleaf.cosmosimserver.chat.application.queries;

import com.qfleaf.cosmosimserver.chat.domain.services.ChatMessageDomainService;
import com.qfleaf.cosmosimserver.chat.interfaces.rest.response.MessageQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageQueryService {
    private final ChatMessageDomainService domainService;

    public List<MessageQueryResponse> getAll(long loginIdAsLong) {
        return domainService.getAllMessages(loginIdAsLong);
    }
}
