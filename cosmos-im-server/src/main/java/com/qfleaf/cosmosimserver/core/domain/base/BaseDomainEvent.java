package com.qfleaf.cosmosimserver.core.domain.base;

import com.qfleaf.cosmosimserver.core.domain.DomainEvent;

import java.time.Instant;

public abstract class BaseDomainEvent implements DomainEvent {
    private final Instant occurredOn = Instant.now();
    
    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}