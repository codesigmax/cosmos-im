package com.qfleaf.cosmosimserver.user.domain.event;

import com.qfleaf.cosmosimserver.user.domain.aggregates.UserAggregate;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {
    private final UserAggregate user;

    public UserRegisteredEvent(UserAggregate user) {
        super(user);
        this.user = user;
    }
}
