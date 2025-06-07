package com.qfleaf.cosmosimserver.user.domain.event;

import com.qfleaf.cosmosimserver.core.domain.base.BaseDomainEvent;
import com.qfleaf.cosmosimserver.user.domain.valueobjects.UserId;
import lombok.Getter;

@Getter
public class UserProfileUpdatedEvent extends BaseDomainEvent {
    private final Long userId;

    public UserProfileUpdatedEvent(UserId userId) {
        this.userId = userId.value();
    }
}
