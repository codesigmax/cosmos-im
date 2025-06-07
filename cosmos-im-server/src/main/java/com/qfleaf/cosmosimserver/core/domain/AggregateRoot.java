package com.qfleaf.cosmosimserver.core.domain;

import java.util.Collection;

public interface AggregateRoot {
    Collection<DomainEvent> domainEvents();
    void clearEvents();
}