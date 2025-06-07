package com.qfleaf.cosmosimserver.user.domain.event;

import com.qfleaf.cosmosimserver.core.domain.base.BaseDomainEvent;
import com.qfleaf.cosmosimserver.user.domain.valueobjects.UserId;
import lombok.Getter;

@Getter
public class UserPasswordChangedEvent extends BaseDomainEvent {
    private final Long userId;

    public UserPasswordChangedEvent(UserId userId) {
        this.userId = userId.value();
    }
}
