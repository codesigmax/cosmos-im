package com.qfleaf.cosmosimserver.core.domain;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredOn();
}