package com.qfleaf.cosmosimserver.user.domain.event;

import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserProfileUpdatedEvent extends ApplicationEvent {
    private final UserAggregate user;

    public UserProfileUpdatedEvent(UserAggregate user) {
        super(user);
        this.user = user;
    }
}
