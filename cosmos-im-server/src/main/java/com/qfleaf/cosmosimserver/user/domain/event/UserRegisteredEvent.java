package com.qfleaf.cosmosimserver.user.domain.event;

import com.qfleaf.cosmosimserver.core.domain.base.BaseDomainEvent;
import lombok.Getter;

@Getter
public class UserRegisteredEvent extends BaseDomainEvent {
    private final Long userId;
    private final String username;

    public UserRegisteredEvent(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
